package com.example.narrative.utils;

import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.entities.*;
import com.example.narrative.entities.enums.TransactionType;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.services.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TransactionHelper {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private BlueprintService blueprintService;

    @Autowired
    private UserService userService;

    public Transaction generateTransaction(UUID id) {
        TransactionRequest transactionRequest = generateTransactionRequest(id);
        User user = userService.getUser();

        Transaction transaction = new Transaction();
        transactionRequest.required.forEach(instruction -> {
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

        if (transaction.getToAccount() == null || transaction.getToAccount().equals("XXX-XXXXX-X")) {
            transaction.setToAccount((transactionRequest.transactionType == TransactionType.SAVINGS) ? transactionRequest.virtualAccount : user.getMasterAccount());
        }

        if (transaction.getFromAccount() == null || transaction.getFromAccount().equals("XXX-XXXXX-X")) {
            transaction.setFromAccount((transactionRequest.transactionType == TransactionType.SAVINGS) ? user.getMasterAccount() : transactionRequest.virtualAccount);
        }
        transaction.setTransactionType(transactionRequest.transactionType);

        return transaction;
    }

    private TransactionRequest generateTransactionRequest(UUID id) {
        Optional<Chapter> optionalChapter = chapterService.findChapter(id);
        if (optionalChapter.isPresent()) {
            Chapter chapter = optionalChapter.get();
            Story story = storyService.getStory(chapter.getStoryId());
            return TransactionRequest.builder()
                    .transactionType(chapter.getTransactionType())
                    .required(ConversionHelper.convertStringToInstructions(chapter.getRequiredInstructions()))
                    .virtualAccount(story.getVirtualAccount())
                    .build();
        }

        Optional<Template> optionalTemplate = templateService.findTemplate(id);
        if (optionalTemplate.isPresent()) {
            Template template = optionalTemplate.get();
            return TransactionRequest.builder()
                    .transactionType(template.getTransactionType())
                    .required(ConversionHelper.convertStringToInstructions(template.getRequiredInstructions()))
                    .virtualAccount("562-98751-2")
                    .build();
        }
        throw new NotFoundException("Unable to find " + id + " in chapter nor template");
    }

    @Data
    @Builder
    private static class TransactionRequest {
        private List<InstructionResponse> required;
        private TransactionType transactionType;
        private String virtualAccount;
    }
}
