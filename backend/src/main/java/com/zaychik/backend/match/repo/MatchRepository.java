package com.zaychik.backend.match.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaychik.backend.match.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

}
