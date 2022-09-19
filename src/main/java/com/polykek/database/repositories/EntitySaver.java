package com.polykek.database.repositories;

import com.google.common.base.CaseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntitySaver {
    private final EntityManager entityManager;

    @Transactional
    public void saveAll(List<?> entities) {


        try {
            for (Object entity : entities) {
                String query = "INSERT INTO ";
                query += CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entities.get(0).getClass().getSimpleName());
                query += " VALUES (";

                int i = 0;
                for (Field field : entities.get(0).getClass().getFields()) {
                    if (i != 0) {
                        query += ", ";
                    }
                    i = 1;
                    if (field.getType().getSimpleName().equals("String")) {
                        query += "'" + field.get(entity) + "'";
                    }
                    else {
                        query += field.get(entity);
                    }
                }

                query += ")";
                entityManager.createNativeQuery(query).executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
