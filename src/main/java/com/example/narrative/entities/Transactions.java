package com.example.narrative.entities;

import com.example.narrative.entities.enums.CreditType;
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
public class Transactions extends Auditable{

    private String id;
    private String fromUser;
    private String fromAccount;
    private String toUser;
    private String toAccount;
    private BigDecimal amount;
    private Date transactionDate;
    private String storyId;
    private String description;
    private CreditType creditType;
    private String chapterId;
}
