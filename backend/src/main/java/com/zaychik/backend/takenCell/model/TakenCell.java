package com.zaychik.backend.takenCell.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.player.entity.Player;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TakenCell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cell_id")
    private Cell cell;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
