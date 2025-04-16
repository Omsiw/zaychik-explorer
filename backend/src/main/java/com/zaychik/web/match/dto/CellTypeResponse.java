package com.zaychik.web.match.dto;

import com.zaychik.backend.cellType.entity.CellType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class CellTypeResponse {
    private Long id;
    private String type;
    private Double movementCost;

    public static CellTypeResponse fromEntity(CellType cellType) {
        return new CellTypeResponse(
            cellType.getId(),
            cellType.getType(),
            cellType.getMovementCost()
        );
    }
}