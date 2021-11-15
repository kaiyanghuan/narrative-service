package com.example.narrative.services;

import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.entities.Instruction;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.InstructionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InstructionService {

    @Autowired
    private InstructionRepository instructionRepository;

    public List<Instruction> getInstructions() {
        return instructionRepository.findAll();
    }

    public Instruction getInstruction(UUID id) {
        return instructionRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Instruction getInstruction(String name) {
        return instructionRepository.findByName(name).orElseThrow(() -> new NotFoundException(name + " does not exist"));
    }

    public Instruction create(Instruction instruction) {
        instruction.setId(UUID.randomUUID().toString());
        return instructionRepository.save(instruction);
    }

    public Instruction update(Instruction otherInstruction, UUID id) {
        Instruction existingInstruction = getInstruction(id);
        updateWith(otherInstruction, existingInstruction);
        return instructionRepository.save(existingInstruction);
    }

    public Instruction update(Instruction otherInstruction, String name) {
        Instruction existingInstruction = getInstruction(name);
        updateWith(otherInstruction, existingInstruction);
        return instructionRepository.save(existingInstruction);
    }

    public void updateWith(Instruction otherInstruction, Instruction existingInstruction) {
        existingInstruction.setName(otherInstruction.getName());
        existingInstruction.setDescription(otherInstruction.getDescription());
        existingInstruction.setFields(otherInstruction.getFields());
        existingInstruction.setDisplayFormat(otherInstruction.getDisplayFormat());
    }
}
