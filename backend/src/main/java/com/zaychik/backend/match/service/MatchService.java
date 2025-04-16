package com.zaychik.backend.match.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.cell.repo.CellRepository;
import com.zaychik.backend.cellType.repo.CellTypeRepository;
import com.zaychik.backend.cellType.entity.CellType;
import com.zaychik.backend.match.entity.Match;
import com.zaychik.backend.match.repo.MatchRepository;
import com.zaychik.backend.user.entity.User;
import com.zaychik.backend.user.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final CellTypeRepository cellTypeRepository;
    private final CellRepository cellRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public Cell createMatch(User player) {
        // 1. Сначала создаем и сохраняем Match без клеток
        Match match = new Match(LocalDateTime.now());
        match = matchRepository.save(match);
    
        // 2. Получаем типы клеток
        List<CellType> types = cellTypeRepository.findAll();
        if (types.isEmpty()) {
            throw new IllegalStateException("Нет доступных типов клеток!");
        }
    
        // 3. Создаем клетки
        Random random = new Random();
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CellType type = types.get(random.nextInt(types.size()));
            cells.add(new Cell(i, type, match)); // Ссылаемся на сохраненный match
        }
    
        // 4. Сохраняем клетки
        cellRepository.saveAll(cells);
    
        // 5. Обновляем Match с сохраненными клетками
        match.setCells(cells);
        match = matchRepository.save(match);
    
        // 6. Назначаем игроку случайную клетку
        Cell startCell = cells.get(random.nextInt(cells.size()));
        player.setCell(startCell);
        player.setMatch(match);
        userRepository.save(player);
    
        return startCell;
    }
}

