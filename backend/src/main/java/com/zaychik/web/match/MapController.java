package com.zaychik.web.match;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.cell.repo.CellRepository;
import com.zaychik.web.match.dto.CellResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MapController {

    private final CellRepository cellRepository;

    @GetMapping("/{matchId}/cells")
    public ResponseEntity<List<CellResponse>> getMapState(@PathVariable Long matchId) {
        List<Cell> cells = cellRepository.findByMatchId(matchId);
        List<CellResponse> cellResponses = cells.stream()
                .map(CellResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cellResponses);
    }
}