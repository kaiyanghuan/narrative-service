package com.example.narrative.controllers.responses;

import com.example.narrative.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructionResponse {
    private String name;
    private String description;
    private List<FieldResponse> fields;
    private State state;
    private String displayFormat;
}
