package com.example.narrative.controllers.responses;


import com.example.narrative.entities.enums.CreditType;
import com.example.narrative.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetail {
    private BigDecimal amount;
    private String description;
    private CreditType creditType;
    private TransactionType transactionType;
}