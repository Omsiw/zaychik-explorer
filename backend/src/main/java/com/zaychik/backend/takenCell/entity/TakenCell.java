package com.zaychik.backend.takenCell.entity;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.player.entity.Player;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
