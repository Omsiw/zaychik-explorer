package com.zaychik.web.action.dto;

import com.zaychik.web.match.dto.CellResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TakeCellResponse {
    private Long cellId;
    private Long userId;
    private boolean success;
    private String message;
    private List<CellResponse> adjacentCells; 
}