package com.example.narrative.controllers.requests;

import com.example.narrative.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTriggerRequest {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String description;
    private String chapterId;
    private TransactionType transactionType;
}
