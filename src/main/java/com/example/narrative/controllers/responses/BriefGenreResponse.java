package com.example.narrative.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BriefGenreResponse {
    private String id;
    private String name;
    private String description;
    private String requiredInstructions;
    private String chooseOneInstructions;
    private String addOnInstructions;
}
