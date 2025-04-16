package com.zaychik.backend.match.service;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.cell.repo.CellRepository;
import com.zaychik.backend.match.entity.Match;
import com.zaychik.backend.match.repo.MatchRepository;
import com.zaychik.backend.movementHistory.entity.MovementHistory;
import com.zaychik.backend.movementHistory.repo.MovementHistoryRepository;
import com.zaychik.backend.user.entity.User;
import com.zaychik.backend.user.repo.UserRepository;
import com.zaychik.web.action.dto.MoveRequest;
import com.zaychik.web.match.dto.CellResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final UserRepository userRepository;
    private final CellRepository cellRepository;
    private final MatchRepository matchRepository;
    private final MovementHistoryRepository movementHistoryRepository;

    @Transactional
    public CellResponse moveToCell(User user, MoveRequest request) {
        // Получаем текущую клетку игрока

        Cell currentCell = cellRepository.findByIdWithRelations(user.getCell().getId())
        .orElseThrow(() -> new IllegalStateException("Player cell not found"));

        // Получаем матч
        Match match = matchRepository.findById(request.getMatchId())
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));

        // Проверяем, что игрок находится в этом матче
        if (!currentCell.getMatch().getId().equals(match.getId())) {
            throw new IllegalArgumentException("Player is not in this match");
        }

        // Находим целевую клетку по номеру и матчу
        Cell targetCell = cellRepository.findByCellNumAndMatch(request.getTargetCellNum(), match)
                .orElseThrow(() -> new IllegalArgumentException("Target cell not found"));

        // Проверяем, что клетки соседние
        if (!isAdjacent(currentCell, targetCell)) {
            throw new IllegalArgumentException("Cells must be adjacent");
        }

        // Сохраняем историю перемещения
        MovementHistory history = new MovementHistory();
        history.setPlayer(user);
        history.setLastCell(currentCell);
        history.setNewCell(targetCell);
        movementHistoryRepository.save(history);

        // Обновляем позицию игрока
        user.setCell(targetCell);
        userRepository.save(user);

        return CellResponse.fromEntity(targetCell);
    }

    private boolean isAdjacent(Cell cell1, Cell cell2) {
        // Реализация проверки соседства
        int diff = Math.abs(cell1.getCellNum() - cell2.getCellNum());
        return diff == 1 || diff == 10; // Для сетки 10x10
    }
}