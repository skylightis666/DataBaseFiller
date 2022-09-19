package com.polykek.database.repositories;

import com.polykek.database.entities.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StructureRepository extends JpaRepository<Structure, Integer> {
}