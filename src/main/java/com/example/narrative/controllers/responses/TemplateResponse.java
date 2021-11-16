package com.example.narrative.controllers.responses;

import com.example.narrative.entities.enums.State;
import com.example.narrative.entities.enums.TransactionPattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateResponse {
    private String id;
    private String name;
    private String description;
    private String blueprintId;
    private List<InstructionResponse> requiredInstructions;
    private List<InstructionResponse> chooseOneInstructions;
    private List<InstructionResponse> addOnInstructions;
    private TransactionPattern transactionPattern;
    private State state = State.INACTIVE;
}