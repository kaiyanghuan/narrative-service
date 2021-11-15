package com.example.narrative.controllers.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreRequest {

    private String name;
    private String description;
    private List<String> requiredInstructions;
    private List<String> chooseOneInstructions;
    private List<String> addOnInstructions;
}
