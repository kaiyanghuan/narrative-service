package com.example.narrative.controllers.responses;

import com.example.narrative.entities.enums.State;
import com.example.narrative.entities.enums.TransactionPattern;
import com.example.narrative.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterResponse {
    private String id;
    private String name;
    private String description;
    private String storyId;
    private List<InstructionResponse> requiredInstructions;
    private List<InstructionResponse> chooseOneInstructions;
    private List<InstructionResponse> addOnInstructions;
    private TransactionPattern transactionPattern;
    private TransactionType transactionType;
    private State state = State.INACTIVE;
}
