package com.zaychik.backend.cell.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaychik.backend.cell.entity.Cell;

public interface CellRepository extends JpaRepository<Cell, Long> {
}
