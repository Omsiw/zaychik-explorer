package com.zaychik.backend.match.service;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.cell.repo.CellRepository;
import com.zaychik.backend.match.entity.Match;
import com.zaychik.backend.match.repo.MatchRepository;
import com.zaychik.backend.takenCell.entity.TakenCell;
import com.zaychik.backend.takenCell.repo.TakenCellRepository;
import com.zaychik.backend.user.entity.User;
import com.zaychik.backend.user.repo.UserRepository;
import com.zaychik.web.action.dto.TakeCellRequest;
import com.zaychik.web.action.dto.TakeCellResponse;
import com.zaychik.web.match.dto.CellResponse;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TakenCellService {

    private final TakenCellRepository takenCellRepository;
    private final CellRepository cellRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    @Transactional
    public TakeCellResponse takeCell(TakeCellRequest request, User currentUser) {

        Cell cell = cellRepository.findByIdWithRelations(request.getCellId())
                .orElseThrow(() -> new IllegalArgumentException("Cell not found"));

        Match match = matchRepository.findById(request.getMatchId())
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));


        if (!cell.getMatch().getId().equals(match.getId())) {
            return new TakeCellResponse(
                    cell.getId(),
                    currentUser.getId(),
                    false,
                    "Cell does not belong to this match",
                    List.of()
            );
        }

        if (takenCellRepository.existsByCell(cell)) {
            return new TakeCellResponse(
                    cell.getId(),
                    currentUser.getId(),
                    false,
                    "Cell is already taken",
                    List.of()
            );
        }


        currentUser.setCell(cell);
        currentUser.setMatch(match);
        userRepository.save(currentUser);

        TakenCell takenCell = new TakenCell();
        takenCell.setCell(cell);
        takenCell.setUser(currentUser);
        takenCellRepository.save(takenCell);
    

        List<Cell> adjacentCells = findAdjacentCells3x3(cell);
        List<CellResponse> adjacentCellsDto = adjacentCells.stream()
                .map(CellResponse::fromEntity)
                .toList();
    
        return new TakeCellResponse(
                cell.getId(),
                currentUser.getId(),
                true,
                "Cell successfully taken",
                adjacentCellsDto
        );
    }
    
    @Cacheable("adjacentCells")
    private List<Cell> findAdjacentCells3x3(Cell centerCell) {
        int centerNum = centerCell.getCellNum();
        int centerX = centerNum % 10; 
        int centerY = centerNum / 10;

        int minX = Math.max(0, centerX - 1);
        int maxX = Math.min(9, centerX + 1);
        int minY = Math.max(0, centerY - 1);
        int maxY = Math.min(9, centerY + 1);
        

        List<Integer> cellNumbers = new ArrayList<>();
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                int num = y * 10 + x;
                if (num != centerNum) {
                    cellNumbers.add(num);
                }
            }
        }
        
        return cellRepository.findByCellNumInAndMatch(cellNumbers, centerCell.getMatch());
    }
}