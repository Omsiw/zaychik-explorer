package com.zaychik.backend.cell.entity;

import com.zaychik.backend.cellType.entity.CellType;
import com.zaychik.backend.match.entity.Match;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cellNum;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "cell_type_id")
    private CellType cellType;

    public Cell(Integer cellNum, CellType cellType, Match match) {
        this.cellNum = cellNum;
        this.cellType = cellType;
        this.match = match;
    }

    @Deprecated
    protected Cell(){
    }
}
