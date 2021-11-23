package com.example.narrative.utils;

import com.example.narrative.controllers.responses.FieldResponse;
import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.entities.Blueprint;
import com.example.narrative.entities.Chapter;
import com.example.narrative.entities.Template;
import com.example.narrative.entities.enums.State;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TemplateHelper {

    public Template convertStoryToTemplate(Chapter chapter, Blueprint blueprint, Boolean maskChoice) {
        // Convert all Strings into Instruction Responses
        List<InstructionResponse> addOnInstructions = ConversionHelper.convertStringToInstructions(chapter.getAddOnInstructions());
        List<InstructionResponse> chooseOneInstructions = ConversionHelper.convertStringToInstructions(chapter.getChooseOneInstructions());
        List<InstructionResponse> requiredInstructions = ConversionHelper.convertStringToInstructions(chapter.getRequiredInstructions());

        // Mask each individual instructions and update instruction state
        masking(addOnInstructions, maskChoice);
        masking(chooseOneInstructions, maskChoice);
        masking(requiredInstructions, maskChoice);

        return Template.builder()
                .id(UUID.randomUUID().toString())
                .name(chapter.getName())
                .description(chapter.getDescription())
                .blueprintId(blueprint.getId())
                .addOnInstructions(ConversionHelper.convertInstructionsToString(addOnInstructions))
                .chooseOneInstructions(ConversionHelper.convertInstructionsToString(chooseOneInstructions))
                .requiredInstructions(ConversionHelper.convertInstructionsToString(requiredInstructions))
                .transactionType(chapter.getTransactionType())
                .transactionPattern(chapter.getTransactionPattern())
                .state(meetChapterRequirements(chooseOneInstructions, requiredInstructions, blueprint.getTags()) ? State.ACTIVE : State.INACTIVE)
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

    private boolean meetChapterRequirements(List<InstructionResponse> chooseOneInstructions, List<InstructionResponse> requiredInstructions, String tags) {
        if (tags.contains("marketplace")){
            return true;
        }

        for (InstructionResponse required : requiredInstructions) {
            if (required.getState() == State.INACTIVE) {
                return false;
            }
        }
        long count = chooseOneInstructions.stream().filter(instructionResponse -> instructionResponse.getState() == State.ACTIVE).count();
        return count == 1;
    }
}
