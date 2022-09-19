package com.polykek.database.repositories;

import com.polykek.database.entities.Science;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScienceRepository extends JpaRepository<Science, Integer> {
}