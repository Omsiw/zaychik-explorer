package com.zaychik.web.action.dto;

import java.util.List;

import com.zaychik.backend.cell.entity.Cell;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TakenCellsResponse {
    private List<Cell> TakenByEnemy;
    private List<Cell> TakenByUser;
}
