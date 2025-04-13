package com.zaychik.web.auth;

import com.zaychik.backend.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/start")
    public ResponseEntity<Long> startMatch() {
        Long matchId = matchService.createMatch();
        return ResponseEntity.ok(matchId);
    }
}