package com.zaychik.backend.match.service;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.cell.repo.CellRepository;
import com.zaychik.backend.cellType.entity.CellType;
import com.zaychik.backend.cellType.repo.CellTypeRepository;
import com.zaychik.backend.match.entity.Match;
import com.zaychik.backend.match.repo.MatchRepository;
import com.zaychik.backend.user.entity.User;
import com.zaychik.backend.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors; 
import java.util.stream.IntStream;   

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final CellTypeRepository cellTypeRepository;
    private final CellRepository cellRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<Long, Cell> createMatchForPlayers(List<User> players) {
        if (players == null || players.size() != 4) {
            throw new IllegalArgumentException("Для создания матча требуется ровно 4 игрока.");
        }

        Match match = new Match(LocalDateTime.now());
        match = matchRepository.save(match);

        List<CellType> types = cellTypeRepository.findAll();
        if (types.isEmpty()) {
            throw new IllegalStateException("В базе данных нет типов клеток!");
        }
        Random random = new Random();
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CellType type = types.get(random.nextInt(types.size()));
            cells.add(new Cell(i, type, match));
        }
        cellRepository.saveAll(cells);
        match.setCells(cells);

        List<Integer> allCellNumbers = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        Collections.shuffle(allCellNumbers);
        List<Integer> startPositions = allCellNumbers.subList(0, 4);


        Map<Long, Cell> startingCellsMap = new HashMap<>();

        for (int i = 0; i < players.size(); i++) {
            User player = players.get(i);
            int cellNum = startPositions.get(i); 
            
            Cell startCell = cells.stream()
                                  .filter(c -> c.getCellNum() == cellNum)
                                  .findFirst()
                                  .orElseThrow(() -> new IllegalStateException("Стартовая клетка не найдена: " + cellNum));

            player.setMatch(match);
            player.setCell(startCell);
            userRepository.save(player);
            
            startCell.setOwner(player);
            cellRepository.save(startCell);

            startingCellsMap.put(player.getId(), startCell);
        }

        return startingCellsMap;
    }
}