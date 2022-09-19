package com.polykek.database.repositories;

import com.polykek.database.entities.Grid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GridRepository extends JpaRepository<Grid, Integer> {
}