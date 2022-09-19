package com.polykek.database.repositories;

import com.polykek.database.entities.CellAutomaton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CellAutomatonRepository extends JpaRepository<CellAutomaton, Long> {

}
