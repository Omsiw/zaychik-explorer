package com.zaychik.web.action;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.match.service.TakenCellService;
import com.zaychik.backend.takenCell.entity.TakenCell;
import com.zaychik.backend.takenCell.repo.TakenCellRepository;
import com.zaychik.backend.user.entity.User;
import com.zaychik.web.action.dto.GameEvent;
import com.zaychik.web.action.dto.TakeCellRequest;
import com.zaychik.web.action.dto.TakeCellResponse;
import com.zaychik.web.action.dto.TakenCellsResponse;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/action")
@RequiredArgsConstructor
public class TakenCellController {

    private final TakenCellService takenCellService;
    private final SimpMessagingTemplate messagingTemplate;
    private final TakenCellRepository takenCellRepository;

    @PostMapping("/take")
    public ResponseEntity<TakeCellResponse> takeCell(
            @AuthenticationPrincipal User user,
            @RequestBody TakeCellRequest request) {
        TakeCellResponse response = takenCellService.takeCell(request, user);
        
        if (response.isSuccess()) {
            GameEvent event = GameEvent.builder()
                    .eventType(GameEvent.EventType.TAKE_CELL)
                    .userId(user.getId())
                    .cellNum(response.getAdjacentCells().get(0).getCellNum())
                    .build();

            String destination = "/topic/match/" + request.getMatchId();
            
            messagingTemplate.convertAndSend(destination, event);
            System.out.println("[BROADCAST] Отправлено событие TAKE_CELL в топик " + destination);
        
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/taken")
    public ResponseEntity<TakenCellsResponse> GetTakenCells(@AuthenticationPrincipal User user) {
        List<TakenCell> allTakenCells = takenCellRepository.findAllByCell_Match_Id(user.getMatch().getId());
        List<Cell> userCells = allTakenCells.stream()
                                    .filter(tc -> tc.getUser().getId().equals(user.getId()))
                                    .map(TakenCell::getCell).toList();
        List<Cell> enemyCells = allTakenCells.stream()
                                    .filter(tc -> tc.getUser().getId().equals(user.getId()))
                                    .map(TakenCell::getCell).toList();
        TakenCellsResponse response = new TakenCellsResponse(userCells, enemyCells);
        return ResponseEntity.ok(response);
    }
    
}