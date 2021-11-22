package com.example.narrative.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {
    private LocalDate transactionDate;
    private List<TransactionDetail> transactionDetails;
}