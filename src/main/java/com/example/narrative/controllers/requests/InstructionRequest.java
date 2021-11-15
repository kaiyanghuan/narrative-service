package com.example.narrative.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionRequest {

    private String name;
    private String description;
    private List<String> fields;
    private String displayFormat;
}
