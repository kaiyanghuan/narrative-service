package com.example.narrative.controllers;

import com.example.narrative.entities.Blueprint;
import com.example.narrative.services.BlueprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/blueprints")
public class BlueprintController {

    @Autowired
    private BlueprintService blueprintService;

    @GetMapping
    public ResponseEntity<List<Blueprint>> getAllBlueprints() {
        return ok(blueprintService.getBlueprints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blueprint> getBlueprint(@PathVariable UUID id) {
        return ok(blueprintService.getBlueprint(id));
    }

    @GetMapping("/{name}/name")
    public ResponseEntity<Blueprint> getBlueprint(@PathVariable String name) {
        return ok(blueprintService.getBlueprint(name));
    }

    @PostMapping()
    public ResponseEntity<Blueprint> createBlueprint(@RequestBody Blueprint blueprint) {
        return ok(blueprintService.create(blueprint));
    }
}
