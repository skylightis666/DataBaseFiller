package com.polykek.database.repositories;

import com.polykek.database.entities.Partition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartitionRepository extends JpaRepository<Partition, Integer> {
}