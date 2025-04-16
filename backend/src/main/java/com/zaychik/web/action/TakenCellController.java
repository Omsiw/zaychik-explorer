package com.zaychik.web.action;

import com.zaychik.backend.match.service.TakenCellService;
import com.zaychik.backend.user.entity.User;
import com.zaychik.web.action.dto.TakeCellRequest;
import com.zaychik.web.action.dto.TakeCellResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
@RequiredArgsConstructor
public class TakenCellController {

    private final TakenCellService takenCellService;

    @PostMapping("/take")
    public ResponseEntity<TakeCellResponse> takeCell(
            @AuthenticationPrincipal User user,
            @RequestBody TakeCellRequest request) {
        TakeCellResponse response = takenCellService.takeCell(request, user);
        return ResponseEntity.ok(response);
    }
}