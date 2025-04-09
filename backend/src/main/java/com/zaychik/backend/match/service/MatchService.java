package com.zaychik.backend.match.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.cell.repo.CellRepository;
import com.zaychik.backend.cellType.repo.CellTypeRepository;
import com.zaychik.backend.cellType.entity.CellType;
import com.zaychik.backend.match.entity.Match;
import com.zaychik.backend.match.repo.MatchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final CellTypeRepository cellTypeRepository;
    private final CellRepository cellRepository;

    public Long createMatch() {
        Match match = new Match(LocalDateTime.now());
        match = matchRepository.save(match);

        List<CellType> types = cellTypeRepository.findAll();
        Random random = new Random();

        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CellType type = types.get(random.nextInt(types.size()));
            cells.add(new Cell(i, type, match));
        }

        cellRepository.saveAll(cells);
        match.setCells(cells);

        return match.getId();
    }
}

