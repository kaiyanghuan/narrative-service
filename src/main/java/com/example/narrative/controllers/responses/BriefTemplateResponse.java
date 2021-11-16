package com.example.narrative.controllers.responses;

import com.example.narrative.entities.enums.State;
import com.example.narrative.entities.enums.TransactionPattern;
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
    private State state;
}