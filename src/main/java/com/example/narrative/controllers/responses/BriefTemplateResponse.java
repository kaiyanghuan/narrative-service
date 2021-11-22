package com.example.narrative.controllers.responses;

import com.example.narrative.entities.enums.State;
import com.example.narrative.entities.enums.TransactionPattern;
import com.example.narrative.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BriefTemplateResponse {
    private String id;
    private String name;
    private String description;
    private TransactionPattern transactionPattern;
    private TransactionType transactionType;
    private State state;
}