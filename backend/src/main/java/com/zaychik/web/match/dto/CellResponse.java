package com.zaychik.web.match.dto;

import com.zaychik.backend.cell.entity.Cell;
import com.zaychik.backend.cellType.entity.CellType;

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
        // Явно загружаем необходимые данные внутри транзакции
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