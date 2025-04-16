package com.zaychik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({
    "com.zaychik.backend.user.repo",
    "com.zaychik.backend.cell.repo",
    "com.zaychik.backend.cellType.repo",
    "com.zaychik.backend.match.repo",
    "com.zaychik.backend.movementHistory.repo",
    "com.zaychik.backend.status.repo",
    "com.zaychik.backend.takenCell.repo"
})
@EntityScan({
    "com.zaychik.backend.user.entity",
    "com.zaychik.backend.cell.entity",
    "com.zaychik.backend.cellType.entity",
    "com.zaychik.backend.match.entity",
    "com.zaychik.backend.movementHistory.entity",
    "com.zaychik.backend.takenCell.entity",
    "com.zaychik.backend.status.entity"
})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
