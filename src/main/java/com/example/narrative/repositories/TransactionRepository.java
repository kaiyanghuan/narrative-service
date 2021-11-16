package com.example.narrative.repositories;

import com.example.narrative.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);

    List<Transaction> findByStoryId(String storyId);

    List<Transaction> findByChapterId(String chapterId);
}
