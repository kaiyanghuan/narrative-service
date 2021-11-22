package com.example.narrative.controllers.responses;

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
public class BriefGenreResponse {
    private String id;
    private String name;
    private String description;
    private List<String> requiredInstructions;
    private List<String> chooseOneInstructions;
    private List<String> addOnInstructions;
    private TransactionType transactionType;
}
