package com.zaychik.backend.match.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zaychik.backend.match.entity.Match;
import com.zaychik.backend.match.repo.MatchRepository;
import com.zaychik.backend.status.entity.Status;
import com.zaychik.backend.status.repo.StatusRepository;
import com.zaychik.backend.user.entity.User;
import com.zaychik.backend.user.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchMakerService {
    private final Queue<User> searchingQueue = new ConcurrentLinkedQueue<>();
    private final UserRepository playerRepository;
    private final StatusRepository statusRepository;
    private final MatchRepository matchRepository;
    
    @Scheduled(fixedRate = 5000)
    public void processQueue() {
        synchronized (searchingQueue) {
            while (searchingQueue.size() >= 5) {
                List<User> matchPlayers = new ArrayList<>();
                Status inGameStatus = statusRepository.findByName("IN_GAME")
                    .orElseThrow(() -> new IllegalStateException("Status IN_GAME not found"));
                
                for (int i = 0; i < 5; i++) {
                    User player = searchingQueue.poll();
                    player.setStatus(inGameStatus);
                    matchPlayers.add(player);
                }
                
                createMatch(matchPlayers);
            }
        }
    }
    
    private void createMatch(List<User> players) {
        Match match = new Match(LocalDateTime.now());
        matchRepository.save(match);
        
        players.forEach(p -> {
            p.setMatch(match);
            playerRepository.save(p);
        });
    }
    
    public void addToQueue(User player) {
        Status searchingStatus = statusRepository.findByName("SEARCHING")
            .orElseThrow(() -> new IllegalStateException("Status SEARCHING not found"));
        
        player.setStatus(searchingStatus);
        playerRepository.save(player);
        searchingQueue.add(player);
    }
}