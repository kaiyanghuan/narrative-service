package com.example.narrative.controllers.requests;


import com.example.narrative.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private TransactionType transactionType;
}
