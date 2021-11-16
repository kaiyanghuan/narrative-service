package com.example.narrative.services;

import com.example.narrative.entities.Transaction;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public List<Transaction> getTransactions() {
        return transactionRepository.findByUserId(userService.getUserId());
    }

    public List<Transaction> getTransactionsByStory(String storyId) {
        return transactionRepository.findByStoryId(storyId);
    }

    public List<Transaction> getTransactionsByChapter(String chapterId) {
        return transactionRepository.findByChapterId(chapterId);
    }

    public Transaction getTransaction(UUID id) {
        return transactionRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Transaction create(Transaction transaction) {
        transaction.setUserId(userService.getUserId());
        return transactionRepository.save(transaction);
    }

    public Transaction place(UUID id, String storyId) {
        Transaction transaction = getTransaction(id);
        transaction.setStoryId(storyId);
        return transactionRepository.save(transaction);
    }

    public Transaction delete(UUID id) {
        Transaction transaction = getTransaction(id);
        transactionRepository.delete(transaction);
        return transaction;
    }
}
