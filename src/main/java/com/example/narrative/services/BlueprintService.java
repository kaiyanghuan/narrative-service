package com.example.narrative.services;

import com.example.narrative.entities.Blueprint;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.BlueprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class BlueprintService {

    @Autowired
    private BlueprintRepository blueprintRepository;

    public List<Blueprint> getBlueprints() {
        return blueprintRepository.findAll();
    }

    public Blueprint getBlueprint(UUID id) {
        return blueprintRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Blueprint getBlueprint(String name) {
        return blueprintRepository.findByName(name).orElseThrow(() -> new NotFoundException(name + " does not exist"));
    }

    public Blueprint create(Blueprint blueprint) {
        return blueprintRepository.save(blueprint);
    }
}