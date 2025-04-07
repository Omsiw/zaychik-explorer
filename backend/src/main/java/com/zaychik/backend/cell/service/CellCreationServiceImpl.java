package com.zaychik.backend.cell.service;

import org.springframework.stereotype.Service;

import com.zaychik.backend.cell.model.CellCreationParam;
import com.zaychik.backend.cell.repo.CellRepository;

@Service
public class CellCreationServiceImpl implements CellCreationService {

    CellRepository cellRepo;

    @Override
    public Long createCell(CellCreationParam param) {
        
    }
    
}
