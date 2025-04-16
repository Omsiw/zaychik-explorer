package com.zaychik.backend.movementHistory.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaychik.backend.movementHistory.entity.MovementHistory;

public interface MovementHistoryRepository extends JpaRepository<MovementHistory, Long>{
    
}
