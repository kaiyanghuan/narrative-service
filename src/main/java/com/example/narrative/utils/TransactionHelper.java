package com.example.narrative.utils;

import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.entities.Chapter;
import com.example.narrative.entities.Story;
import com.example.narrative.entities.Transaction;
import com.example.narrative.services.ChapterService;
import com.example.narrative.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionHelper {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private StoryService storyService;

    public Transaction generateTransaction(UUID chapterId) {
        Chapter chapter = chapterService.getChapter(chapterId);
        Story story = storyService.getStory(chapter.getStoryId());
        List<InstructionResponse> required = ConversionHelper.convertStringToInstructions(chapter.getRequiredInstructions());
        Transaction transaction = new Transaction();
        required.forEach(instruction -> {
            instruction.getFields().forEach(field -> {
                if (field.getFieldName().equals("description")) {
                    transaction.setDescription(field.getValue());
                }
                if (field.getFieldName().equals("amount")) {
                    transaction.setAmount(BigDecimal.valueOf(Double.parseDouble(field.getValue())));
                }
                if (field.getFieldName().equals("toAccount")) {
                    transaction.setToAccount(field.getValue());
                }
                if (field.getFieldName().equals("fromAccount")) {
                    transaction.setFromAccount(field.getValue());
                }
            });
        });

        if (transaction.getToAccount() == null) {
            transaction.setToAccount(story.getVirtualAccount());
        }

        if (transaction.getFromAccount() == null) {
            transaction.setFromAccount(story.getVirtualAccount());
        }
        transaction.setTransactionType(chapter.getTransactionType());

        return transaction;
    }
}
