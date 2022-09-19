package com.polykek.database.repositories;

import com.polykek.database.entities.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
}