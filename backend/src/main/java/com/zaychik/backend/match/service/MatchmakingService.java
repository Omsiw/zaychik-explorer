package com.zaychik.backend.match.service;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.user.entity.User;
import com.zaychik.web.match.dto.CellResponse;
import com.zaychik.web.match.dto.MatchFoundResponse;
import com.zaychik.web.match.dto.PlayerPositionDto;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchmakingService {

    private final List<User> queue = new ArrayList<>();
    private final Object queueLock = new Object(); 

    private final MatchService matchService;
    private final SimpMessagingTemplate messagingTemplate;

    public void addUserToQueue(User user) {
        List<User> matchedPlayers = null;

        synchronized (queueLock) {
            if (queue.contains(user)) {
                return; 
            }
            
            queue.add(user);
            System.out.println("User " + user.getUsername() + " added to queue. Current size: " + queue.size());

            if (queue.size() >= 4) {
                matchedPlayers = new ArrayList<>(queue.subList(0, 4));
                queue.subList(0, 4).clear();
            }
        }

        if (matchedPlayers != null) {
            startMatchForPlayers(matchedPlayers);
        }
    }

    private void startMatchForPlayers(List<User> players) {
        System.out.println("Found 4 players. Starting match...");
        Map<Long, Cell> startingCells = matchService.createMatchForPlayers(players);

        List<PlayerPositionDto> allPlayerPositions = players.stream()
            .map(p -> new PlayerPositionDto(
                p.getId(),
                p.getUsername(),
                startingCells.get(p.getId()).getCellNum()
            ))
            .collect(Collectors.toList());

        for (PlayerPositionDto currentPlayer : allPlayerPositions) {
            
            List<PlayerPositionDto> enemies = allPlayerPositions.stream()
                .filter(p -> !p.getUserId().equals(currentPlayer.getUserId()))
                .collect(Collectors.toList());

            MatchFoundResponse response = new MatchFoundResponse(
                startingCells.get(currentPlayer.getUserId()).getMatch().getId(),
                currentPlayer.getCellNum(), 
                enemies 
            );

            String destination = "/topic/user/" + currentPlayer.getUsername() + "/match-found";
            
            messagingTemplate.convertAndSend(destination, response);
            System.out.println("Sent match found notification to user " + currentPlayer.getUsername());
        }
    }
}