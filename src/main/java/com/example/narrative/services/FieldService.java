package com.example.narrative.services;

import com.example.narrative.entities.Field;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    public List<Field> getFields() {
        return fieldRepository.findAll();
    }

    public Field getField(UUID id) {
        return fieldRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Field getField(String fieldName) {
        return fieldRepository.findByFieldName(fieldName).orElseThrow(() -> new NotFoundException(fieldName + " does not exist"));
    }

    public Field create(Field field) {
        return fieldRepository.save(field);
    }

    public Field update(Field otherField, UUID id) {
        Field existingField = getField(id);
        updateWith(otherField, existingField);
        return fieldRepository.save(existingField);
    }

    public Field update(Field otherField, String fieldName) {
        Field existingField = getField(fieldName);
        updateWith(otherField, existingField);
        return fieldRepository.save(existingField);
    }

    public void updateWith(Field otherField, Field existingField) {
        existingField.setFieldName(otherField.getFieldName());
        existingField.setDataType(otherField.getDataType());
        existingField.setPlaceholder(otherField.getPlaceholder());
        existingField.setMask(otherField.getMask());
        existingField.setRequired(otherField.getRequired());
        existingField.setValue(otherField.getValue());
        existingField.setMaskedValue(otherField.getMaskedValue());
        existingField.setDisplayName(otherField.getDisplayName());
    }
}
