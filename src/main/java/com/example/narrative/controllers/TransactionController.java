package com.example.narrative.controllers;

import com.example.narrative.controllers.requests.TransactionRequest;
import com.example.narrative.controllers.requests.TransactionTriggerRequest;
import com.example.narrative.controllers.responses.TransactionHistoryResponse;
import com.example.narrative.controllers.responses.TransactionResponse;
import com.example.narrative.services.TransactionService;
import com.example.narrative.utils.RequestHelper;
import com.example.narrative.utils.ResponseHelper;
import com.example.narrative.utils.TransactionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RequestHelper requestHelper;

    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private TransactionHelper transactionHelper;

    @GetMapping
    public ResponseEntity<TransactionHistoryResponse> getAllTransactions() {
        return ok(responseHelper.from(transactionService.getTransactions()).toTransactionHistoryResponse());
    }

    @GetMapping("/story/{storyId}")
    public ResponseEntity<TransactionHistoryResponse> getAllTransactionsByStory(@PathVariable String storyId) {
        return ok(responseHelper.from(transactionService.getTransactionsByStory(storyId)).toTransactionHistoryResponse());
    }

    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<TransactionHistoryResponse> getAllTransactionsByChapter(@PathVariable String chapterId) {
        return ok(responseHelper.from(transactionService.getTransactionsByChapter(chapterId)).toTransactionHistoryResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable UUID id) {
        return ok(responseHelper.from(transactionService.getTransaction(id)).toTransactionResponse());
    }

    @GetMapping("/chapter/{chapterId}/request")
    public ResponseEntity<TransactionResponse> requestTransaction(@PathVariable UUID chapterId) {
        return ok(responseHelper.from(transactionHelper.generateTransaction(chapterId)).toTransactionResponse());
    }

    @PostMapping("/trigger")
    public ResponseEntity<TransactionResponse> triggerTransaction(@RequestBody TransactionTriggerRequest transactionTriggerRequest) {
        return ok(responseHelper.from(transactionService.create(requestHelper.from(transactionTriggerRequest).toTransaction())).toTransactionResponse());
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ok(responseHelper.from(transactionService.create(requestHelper.from(transactionRequest).toTransaction())).toTransactionResponse());
    }

    @PutMapping("/{id}/story/{storyId}")
    public ResponseEntity<TransactionResponse> placeTransaction(@PathVariable UUID id, @PathVariable String storyId) {
        return ok(responseHelper.from(transactionService.place(id, storyId)).toTransactionResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionResponse> deleteTransaction(@PathVariable UUID id) {
        return ok(responseHelper.from(transactionService.delete(id)).toTransactionResponse());
    }
}
