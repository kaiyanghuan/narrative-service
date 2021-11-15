package com.example.narrative.repositories;

import com.example.narrative.entities.Blueprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlueprintRepository extends JpaRepository<Blueprint, String> {
    Optional<Blueprint> findByName(String name);
}
