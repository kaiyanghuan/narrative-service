package com.example.narrative.repositories;

import com.example.narrative.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
    Optional<Field> findByFieldName(String fieldName);
}
