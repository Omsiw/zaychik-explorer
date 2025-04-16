package com.zaychik.web.match;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.match.service.MatchMakerService;
import com.zaychik.backend.match.service.MatchService;
import com.zaychik.backend.user.entity.User;
import com.zaychik.backend.user.repo.UserRepository;
import com.zaychik.web.match.dto.CellResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class StartMatchController {
    
    private final MatchService matchService;
    
    @PostMapping("/search")
    public ResponseEntity<CellResponse> startSearch(@AuthenticationPrincipal User user) {
        Cell cell = matchService.createMatch(user);
        return ResponseEntity.ok(CellResponse.fromEntity(cell));
    }
}
