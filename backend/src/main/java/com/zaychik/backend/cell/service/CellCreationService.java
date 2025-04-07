package com.zaychik.backend.cell.service;

import com.zaychik.backend.cell.model.CellCreationParam;

public interface CellCreationService {
    Long createCell(CellCreationParam param);
}
