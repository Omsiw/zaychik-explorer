package com.zaychik.backend.status.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaychik.backend.status.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
    
    Optional<Status> findByName(String name);
}
