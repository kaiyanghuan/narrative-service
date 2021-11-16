package com.example.narrative.utils;

import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.exceptions.BusinessException;
import com.example.narrative.exceptions.ConversionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConversionHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<InstructionResponse> convertStringToInstructions(String instructions) {
        try {
            return objectMapper.readValue(instructions, new TypeReference<List<InstructionResponse>>() {
            });
        } catch (JsonProcessingException e) {
            throw new ConversionException("Unable to convert: " + instructions);
        }
    }

    public static List<String> convertStringToList(String value) {
        try {
            return objectMapper.readValue(value, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new ConversionException("Unable to convert: " + value);
        }
    }

    public static String convertListToString(List<String> stringList) {
        try {
            return objectMapper.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Unable to convert: " + stringList.toString());
        }
    }

    public static String convertInstructionsToString(List<InstructionResponse> instructionResponses) {
        try {
            return objectMapper.writeValueAsString(instructionResponses);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Unable to convert: " + instructionResponses.toString());
        }
    }
}
