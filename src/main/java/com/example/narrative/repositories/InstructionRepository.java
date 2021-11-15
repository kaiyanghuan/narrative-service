package com.example.narrative.repositories;

import com.example.narrative.entities.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, String> {
    Optional<Instruction> findByName(String name);
}