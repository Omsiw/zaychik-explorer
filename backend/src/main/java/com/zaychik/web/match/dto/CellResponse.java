package com.zaychik.web.match.dto;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.cellType.entity.CellType;
import com.zaychik.backend.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CellResponse {
    private Long id;
    private Integer cellNum;
    private CellTypeResponse cellType;
    private Long matchId;

    public static CellResponse fromEntity(Cell cell) {
        return new CellResponse(
            cell.getId(),
            cell.getCellNum(),
            (cell.getCellType() != null) 
                ? new CellTypeResponse(
                    cell.getCellType().getId(),
                    cell.getCellType().getType(),
                    cell.getCellType().getMovementCost()
                  )
                : null,
            (cell.getMatch() != null) ? cell.getMatch().getId() : null
        );
    }
}