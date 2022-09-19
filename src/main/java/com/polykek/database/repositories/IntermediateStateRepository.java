package com.polykek.database.repositories;

import com.polykek.database.entities.IntermediateState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntermediateStateRepository extends JpaRepository<IntermediateState, Integer> {
}