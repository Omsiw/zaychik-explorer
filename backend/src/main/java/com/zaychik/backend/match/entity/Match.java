package com.zaychik.backend.match.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.zaychik.backend.cell.entity.Cell;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Long winnerId; 

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<Cell> cells = new ArrayList<>();

    public Match(LocalDateTime startTime) {
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(15);
    }
}
