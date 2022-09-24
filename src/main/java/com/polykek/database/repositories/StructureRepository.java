package com.polykek.database.repositories;

import com.polykek.database.entities.FinalState;
import com.polykek.database.entities.Structure;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StructureRepository extends JpaRepository<Structure, Integer> {
}