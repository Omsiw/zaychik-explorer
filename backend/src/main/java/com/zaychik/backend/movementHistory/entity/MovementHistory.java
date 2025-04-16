package com.zaychik.backend.movementHistory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.user.entity.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private User player;

    @ManyToOne
    @JoinColumn(name = "last_cell_id")
    private Cell lastCell;

    @ManyToOne
    @JoinColumn(name = "new_cell_id")
    private Cell newCell;
}
