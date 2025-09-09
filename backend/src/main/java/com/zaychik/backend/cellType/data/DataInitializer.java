package com.zaychik.backend.cellType.data;

import com.zaychik.backend.cellType.entity.CellType;
import com.zaychik.backend.cellType.repo.CellTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CellTypeRepository cellTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (cellTypeRepository.count() == 0) {
            cellTypeRepository.save(new CellType(null, "Равнина", 1.0));
            cellTypeRepository.save(new CellType(null, "Лес", 1.5));
            cellTypeRepository.save(new CellType(null, "Горы", 2.5));
            cellTypeRepository.save(new CellType(null, "Болото", 2.0));
            cellTypeRepository.save(new CellType(null, "Вода", 5.0));
        }
    }
}
