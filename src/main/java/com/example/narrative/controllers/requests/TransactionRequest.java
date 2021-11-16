package com.example.narrative.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String description;
    private Date transactionDate;
}
