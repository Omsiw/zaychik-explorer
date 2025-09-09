package com.zaychik.web.match;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaychik.backend.match.service.MatchmakingService; // Меняем сервис
import com.zaychik.backend.user.entity.User;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class StartMatchController {
    
    private final MatchmakingService matchmakingService;
    
    @PostMapping("/search")
    public ResponseEntity<String> startSearch(@AuthenticationPrincipal User user) {
        matchmakingService.addUserToQueue(user);
        
        return ResponseEntity.ok("Вы добавлены в очередь поиска матча.");
    }
}