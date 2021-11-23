package com.example.narrative.utils;

import com.example.narrative.controllers.requests.*;
import com.example.narrative.controllers.responses.ChapterResponse;
import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.entities.*;
import com.example.narrative.entities.enums.CreditType;
import com.example.narrative.entities.enums.State;
import com.example.narrative.entities.enums.TransactionPattern;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class RequestHelper {

    public static class GenreRequestHelper {
        private final GenreRequest genreRequest;

        public GenreRequestHelper(GenreRequest genreRequest) {
            this.genreRequest = genreRequest;
        }

        public Genre toGenre() {
            return Genre.builder()
                    .name(genreRequest.getName())
                    .description(genreRequest.getDescription())
                    .addOnInstructions(ConversionHelper.convertListToString(genreRequest.getAddOnInstructions()))
                    .chooseOneInstructions(ConversionHelper.convertListToString(genreRequest.getChooseOneInstructions()))
                    .requiredInstructions(ConversionHelper.convertListToString(genreRequest.getRequiredInstructions()))
                    .transactionType(genreRequest.getTransactionType())
                    .build();
        }
    }


    public static class InstructionRequestHelper {
        private final InstructionRequest instructionRequest;

        public InstructionRequestHelper(InstructionRequest instructionRequest) {
            this.instructionRequest = instructionRequest;
        }

        public Instruction toInstruction() {
            return Instruction.builder()
                    .name(instructionRequest.getName())
                    .description(instructionRequest.getDescription())
                    .fields(ConversionHelper.convertListToString(instructionRequest.getFields()))
                    .displayFormat(instructionRequest.getDisplayFormat())
                    .build();
        }
    }

    public static class StoryRequestHelper {
        private final StoryRequest storyRequest;

        public StoryRequestHelper(StoryRequest StoryRequest) {
            this.storyRequest = StoryRequest;
        }

        public Story toStory() {
            return Story.builder()
                    .name(storyRequest.getName())
                    .description(storyRequest.getDescription())
                    .icon(storyRequest.getIcon())
                    .build();
        }
    }

    public static class ChapterRequestHelper {
        private final ChapterRequest chapterRequest;

        public ChapterRequestHelper(ChapterRequest chapterRequest) {
            this.chapterRequest = chapterRequest;
        }

        public Chapter toChapter() {
            ChapterResponse chapter = chapterRequest.getChapter();
            AtomicReference<String> name = new AtomicReference<>();
            AtomicReference<String> description = new AtomicReference<>();

            Optional<InstructionResponse> instructionResponseOptional = chapter.getRequiredInstructions().stream()
                    .filter(instruction -> instruction.getName().equals("Title")).findAny();
            instructionResponseOptional.ifPresent(instructionResponse -> instructionResponse.getFields()
                    .forEach(field -> {
                        if (field.getFieldName().equals("title")) {
                            name.set(field.getValue());
                        }
                        if (field.getFieldName().equals("description")) {
                            description.set(field.getValue());
                        }
                    }));


            String chapterName = (name.get() == null || name.get().equals("")) ? chapter.getName() : name.get();
            String chapterDescription = (description.get() == null || description.get().equals("")) ? chapter.getName() : description.get();

            return Chapter.builder()
                    .name(chapterName)
                    .description(chapterDescription)
                    .addOnInstructions(ConversionHelper.convertInstructionsToString(chapter.getAddOnInstructions()))
                    .chooseOneInstructions(ConversionHelper.convertInstructionsToString(chapter.getChooseOneInstructions()))
                    .requiredInstructions(ConversionHelper.convertInstructionsToString(chapter.getRequiredInstructions()))
                    .transactionPattern(getTransactionPattern(chapter.getChooseOneInstructions()))
                    .transactionType(chapter.getTransactionType())
                    .state(State.ACTIVE)
                    .build();
        }


        public Template toTemplate() {
            ChapterResponse template = chapterRequest.getChapter();

            return Template.builder()
                    .name(template.getName())
                    .description(template.getDescription())
                    .addOnInstructions(ConversionHelper.convertInstructionsToString(template.getAddOnInstructions()))
                    .chooseOneInstructions(ConversionHelper.convertInstructionsToString(template.getChooseOneInstructions()))
                    .requiredInstructions(ConversionHelper.convertInstructionsToString(template.getRequiredInstructions()))
                    .transactionPattern(template.getTransactionPattern())
                    .transactionType(template.getTransactionType())
                    .state(template.getState())
                    .build();
        }

        private TransactionPattern getTransactionPattern(List<InstructionResponse> chooseOne) {
            Optional<InstructionResponse> instructionResponseOptional = chooseOne.stream().filter(instructionResponse -> instructionResponse.getState() == State.ACTIVE).findAny();
            if (instructionResponseOptional.isPresent()) {
                InstructionResponse instructionResponse = instructionResponseOptional.get();
                if (instructionResponse.getName().equals("One-Time Transaction")) {
                    return TransactionPattern.ONETIME;
                } else if (instructionResponse.getName().equals("Scheduled Transaction")) {
                    return TransactionPattern.SCHEDULED;
                }
                return TransactionPattern.RECURRING;
            }
            return null;
        }
    }

    public static class BlueprintRequestHelper {
        private final BlueprintRequest blueprintRequest;

        public BlueprintRequestHelper(BlueprintRequest blueprintRequest) {
            this.blueprintRequest = blueprintRequest;
        }

        public Blueprint toBlueprint() {
            return Blueprint.builder()
                    .id(UUID.randomUUID().toString())
                    .name(blueprintRequest.getName())
                    .storyName(blueprintRequest.getStoryName())
                    .storyDescription(blueprintRequest.getStoryDescription())
                    .tags(ConversionHelper.convertListToString(blueprintRequest.getTags()))
                    .shareType(blueprintRequest.getShareType())
                    .sharedDate(new Date())
                    .icon(blueprintRequest.getIcon())
                    .adoptionRate(BigInteger.ZERO)
                    .stars(4)
                    .build();
        }
    }

    public static class TransactionRequestHelper {
        private final TransactionRequest transactionRequest;

        public TransactionRequestHelper(TransactionRequest transactionRequest) {
            this.transactionRequest = transactionRequest;
        }

        public Transaction toTransaction() {
            return Transaction.builder()
                    .id(UUID.randomUUID().toString())
                    .fromAccount(transactionRequest.getFromAccount())
                    .toAccount(transactionRequest.getToAccount())
                    .amount(transactionRequest.getAmount())
                    .description(transactionRequest.getDescription())
                    .transactionDate(transactionRequest.getTransactionDate())
                    .creditType((transactionRequest.getAmount().compareTo(BigDecimal.ZERO) < 0) ? CreditType.DEBIT : CreditType.CREDIT)
                    .storyId(transactionRequest.getStoryId())
                    .chapterId(transactionRequest.getChapterId())
                    .transactionType(transactionRequest.getTransactionType())
                    .build();
        }
    }

    public static class TransactionTriggerRequestHelper {
        private final TransactionTriggerRequest transactionRequest;

        public TransactionTriggerRequestHelper(TransactionTriggerRequest transactionRequest) {
            this.transactionRequest = transactionRequest;
        }

        public Transaction toTransaction() {
            return Transaction.builder()
                    .id(UUID.randomUUID().toString())
                    .fromAccount(transactionRequest.getFromAccount())
                    .toAccount(transactionRequest.getToAccount())
                    .amount(transactionRequest.getAmount())
                    .description(transactionRequest.getDescription())
                    .transactionDate(new Date())
                    .chapterId(transactionRequest.getChapterId())
                    .transactionType(transactionRequest.getTransactionType())
                    .creditType((transactionRequest.getAmount().compareTo(BigDecimal.ZERO) < 0) ? CreditType.DEBIT : CreditType.CREDIT)
                    .build();
        }
    }

    public GenreRequestHelper from(GenreRequest genreRequest) {
        return new GenreRequestHelper(genreRequest);
    }

    public InstructionRequestHelper from(InstructionRequest instructionRequest) {
        return new InstructionRequestHelper(instructionRequest);
    }

    public StoryRequestHelper from(StoryRequest StoryRequest) {
        return new StoryRequestHelper(StoryRequest);
    }

    public ChapterRequestHelper from(ChapterRequest chapterRequest) {
        return new ChapterRequestHelper(chapterRequest);
    }

    public BlueprintRequestHelper from(BlueprintRequest blueprintRequest) {
        return new BlueprintRequestHelper(blueprintRequest);
    }

    public TransactionRequestHelper from(TransactionRequest transactionRequest) {
        return new TransactionRequestHelper(transactionRequest);
    }

    public TransactionTriggerRequestHelper from(TransactionTriggerRequest transactionRequest) {
        return new TransactionTriggerRequestHelper(transactionRequest);
    }
}
