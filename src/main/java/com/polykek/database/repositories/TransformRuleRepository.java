package com.polykek.database.repositories;

import com.polykek.database.entities.TransformRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransformRuleRepository extends JpaRepository<TransformRule, Integer> {
//    default void saveAll(List<Object> list) {
//        saveAll((List<TransformRule>)(List<?>) list);
//    }
}