package com.example.narrative.controllers;


import com.example.narrative.entities.Field;
import com.example.narrative.services.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @GetMapping
    public ResponseEntity<List<Field>> getAllFields() {
        return ok(fieldService.getFields());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Field> getField(@PathVariable UUID id) {
        return ok(fieldService.getField(id));
    }

    @GetMapping("/{name}/name")
    public ResponseEntity<Field> getField(@PathVariable String name) {
        return ok(fieldService.getField(name));
    }

    @PostMapping()
    public ResponseEntity<Field> createField(@RequestBody Field field) {
        return ok(fieldService.create(field));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Field> updateField(@RequestBody Field field, @PathVariable UUID id) {
        return ok(fieldService.update(field, id));
    }

    @PutMapping("/{name}/name")
    public ResponseEntity<Field> updateField(@RequestBody Field field, @PathVariable String name) {
        return ok(fieldService.update(field, name));
    }
}
