package com.zaychik.backend.cellType.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.cellType.entity.CellType;

public interface CellTypeRepository extends JpaRepository<CellType, Long> {
}
