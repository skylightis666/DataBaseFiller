package com.polykek.database.repositories;

import com.polykek.database.entities.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellRepository extends JpaRepository<Cell, Integer> {
}