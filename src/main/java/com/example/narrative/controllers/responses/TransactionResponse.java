package com.example.narrative.controllers.responses;

import com.example.narrative.entities.enums.CreditType;
import com.example.narrative.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String description;
    private CreditType creditType;
    private Date transactionDate;
    private String storyId;
    private String chapterId;
    private TransactionType transactionType;
}
