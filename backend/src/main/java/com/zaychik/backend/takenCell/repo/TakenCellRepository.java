package com.zaychik.backend.takenCell.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.takenCell.entity.TakenCell;

public interface TakenCellRepository extends JpaRepository<TakenCell, Long> {
    boolean existsByCell(Cell cell);
}
