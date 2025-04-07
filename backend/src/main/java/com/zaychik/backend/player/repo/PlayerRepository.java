package com.zaychik.backend.player.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaychik.backend.player.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    
    boolean existsByUsername(String username);

    Optional<Player> findByUsername(String username);
}
