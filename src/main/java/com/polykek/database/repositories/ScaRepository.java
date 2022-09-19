package com.polykek.database.repositories;

import com.polykek.database.entities.SCA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScaRepository extends JpaRepository<SCA, Integer> {
}