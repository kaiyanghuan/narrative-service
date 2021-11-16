package com.example.narrative.services;

import com.example.narrative.controllers.responses.FieldResponse;
import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.entities.Chapter;
import com.example.narrative.entities.Template;
import com.example.narrative.entities.enums.State;
import com.example.narrative.exceptions.BusinessException;
import com.example.narrative.exceptions.ConversionException;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.TemplateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Template> getTemplates(String blueprintId) {
        return templateRepository.findAllByBlueprintId(blueprintId);
    }

    public Template getTemplate(UUID id) {
        return templateRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Template create(Chapter chapter, String blueprintId, Boolean mask) {
        return templateRepository.save(convertStoryToTemplate(chapter, blueprintId, mask));
    }

    private Template convertStoryToTemplate(Chapter chapter, String blueprintId, Boolean maskChoice) {
        // Convert all Strings into Instruction Responses
        List<InstructionResponse> addOnInstructions = convertStringToInstructions(chapter.getAddOnInstructions());
        List<InstructionResponse> chooseOneInstructions = convertStringToInstructions(chapter.getChooseOneInstructions());
        List<InstructionResponse> requiredInstructions = convertStringToInstructions(chapter.getRequiredInstructions());

        // Mask each individual instructions and update instruction state
        masking(addOnInstructions, maskChoice);
        masking(chooseOneInstructions, maskChoice);
        masking(requiredInstructions, maskChoice);

        return Template.builder()
                .id(UUID.randomUUID().toString())
                .name(chapter.getName())
                .description(chapter.getDescription())
                .blueprintId(blueprintId)
                .addOnInstructions(convertInstructionsToString(addOnInstructions))
                .chooseOneInstructions(convertInstructionsToString(chooseOneInstructions))
                .requiredInstructions(convertInstructionsToString(requiredInstructions))
                .state(meetChapterRequirements(chooseOneInstructions, requiredInstructions) ? State.ACTIVE : State.INACTIVE)
                .build();
    }

    private void masking(List<InstructionResponse> instructions, Boolean mask) {
        if (mask) {
            instructions.forEach(instruction -> instruction.getFields()
                    .stream()
                    .filter(FieldResponse::getMask)
                    .forEach(field -> field.setValue(field.getMaskedValue()))
            );

            instructions.forEach(instructionResponse -> {
                if (forceInactive(instructionResponse)) {
                    instructionResponse.setState(State.INACTIVE);
                }
            });
        }
    }

    private boolean forceInactive(InstructionResponse instructionResponse) {
        for (FieldResponse field : instructionResponse.getFields()) {
            if (field.getRequired() && field.getValue().equals(field.getMaskedValue())) {
                return true;
            }
        }
        return false;
    }

    private boolean meetChapterRequirements(List<InstructionResponse> chooseOneInstructions, List<InstructionResponse> requiredInstructions) {
        for (InstructionResponse required : requiredInstructions) {
            if (required.getState() == State.INACTIVE) {
                return false;
            }
        }
        long count = chooseOneInstructions.stream().filter(instructionResponse -> instructionResponse.getState() == State.ACTIVE).count();
        return count == 1;
    }

    private List<InstructionResponse> convertStringToInstructions(String instructions) {
        try {
            return objectMapper.readValue(instructions, new TypeReference<List<InstructionResponse>>() {
            });
        } catch (JsonProcessingException e) {
            throw new ConversionException("Unable to convert: " + instructions);
        }
    }

    private String convertInstructionsToString(List<InstructionResponse> instructionResponses) {
        try {
            return objectMapper.writeValueAsString(instructionResponses);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Unable to convert: " + instructionResponses.toString());
        }
    }
}