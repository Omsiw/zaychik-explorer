package com.zaychik.backend.cell.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.match.entity.Match;

public interface CellRepository extends JpaRepository<Cell, Long> {
    @Query("SELECT c FROM Cell c LEFT JOIN FETCH c.cellType LEFT JOIN FETCH c.match WHERE c.id = :id")
    Optional<Cell> findByIdWithRelations(@Param("id") Long id);
    Optional<Cell> findByCellNumAndMatch(Integer cellNum, Match match);
    @Query("SELECT c FROM Cell c LEFT JOIN FETCH c.cellType " +
            "WHERE c.match = :match AND c.cellNum IN :cellNums")
    List<Cell> findByCellNumInAndMatch(
            @Param("cellNums") List<Integer> cellNums,
            @Param("match") Match match);
    @Query("SELECT c FROM Cell c LEFT JOIN FETCH c.cellType LEFT JOIN FETCH c.match " +
            "WHERE c.match.id = :matchId AND c.cellNum IN :cellNums")
     List<Cell> findByCellNumInAndMatchWithRelations(
             @Param("cellNums") List<Integer> cellNums,
             @Param("matchId") Long matchId);
    List<Cell> findByMatchId(Long matchId);
}
