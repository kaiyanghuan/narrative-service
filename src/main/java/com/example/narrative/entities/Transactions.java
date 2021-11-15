package com.example.narrative.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

    private UUID id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private Date transactionDate;
    private UUID storyId;
}
