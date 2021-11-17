package com.example.narrative.utils;

import com.example.narrative.controllers.responses.*;
import com.example.narrative.entities.*;
import com.example.narrative.entities.enums.State;
import com.example.narrative.services.FieldService;
import com.example.narrative.services.InstructionService;
import com.example.narrative.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
public class ResponseHelper {

    @Autowired
    private InstructionService instructionService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private UserService userService;

    public static class ChapterHelper {
        private final Chapter chapter;

        public ChapterHelper(Chapter chapter) {
            this.chapter = chapter;
        }

        public BriefChapterResponse toBriefChapterResponse() {
            return BriefChapterResponse.builder()
                    .id(chapter.getId())
                    .name(chapter.getName())
                    .description(chapter.getDescription())
                    .transactionPattern(chapter.getTransactionPattern())
                    .transactionType(chapter.getTransactionType())
                    .state(chapter.getState())
                    .build();
        }

        public ChapterResponse toChapterResponse() {
            return ChapterResponse.builder()
                    .id(chapter.getId())
                    .name(chapter.getName())
                    .description(chapter.getDescription())
                    .storyId(chapter.getStoryId())
                    .addOnInstructions(ConversionHelper.convertStringToInstructions(chapter.getAddOnInstructions()))
                    .chooseOneInstructions(ConversionHelper.convertStringToInstructions(chapter.getChooseOneInstructions()))
                    .requiredInstructions(ConversionHelper.convertStringToInstructions(chapter.getRequiredInstructions()))
                    .transactionPattern(chapter.getTransactionPattern())
                    .transactionType(chapter.getTransactionType())
                    .state(chapter.getState())
                    .build();
        }
    }

    public class GenreHelper {
        private final Genre genre;

        public GenreHelper(Genre genre) {
            this.genre = genre;
        }

        public BriefGenreResponse toBriefGenreResponse() {
            return BriefGenreResponse.builder()
                    .id(genre.getId())
                    .name(genre.getName())
                    .description(genre.getDescription())
                    .addOnInstructions(genre.getAddOnInstructions())
                    .chooseOneInstructions(genre.getChooseOneInstructions())
                    .requiredInstructions(genre.getRequiredInstructions())
                    .transactionType(genre.getTransactionType())
                    .build();
        }

        public GenreResponse toGenreResponse() {
            return GenreResponse.builder()
                    .id(genre.getId())
                    .name(genre.getName())
                    .description(genre.getDescription())
                    .chapter(generateSampleChapter())
                    .build();
        }

        private ChapterResponse generateSampleChapter() {
            return ChapterResponse.builder()
                    .name(genre.getName())
                    .description(genre.getDescription())
                    .requiredInstructions(generatingInstructionResponses(genre.getRequiredInstructions()))
                    .chooseOneInstructions(generatingInstructionResponses(genre.getChooseOneInstructions()))
                    .addOnInstructions(generatingInstructionResponses(genre.getAddOnInstructions()))
                    .transactionType(genre.getTransactionType())
                    .build();
        }

        private List<InstructionResponse> generatingInstructionResponses(String instructions) {
            return ConversionHelper.convertStringToList(instructions).stream()
                    .map(instruction -> instructionService.getInstruction(instruction))
                    .map(this::convertToInstructionResponse)
                    .collect(Collectors.toList());
        }

        private InstructionResponse convertToInstructionResponse(Instruction instruction) {
            return InstructionResponse.builder()
                    .id(instruction.getId())
                    .name(instruction.getName())
                    .description(instruction.getDescription())
                    .state(State.INACTIVE)
                    .fields(ConversionHelper.convertStringToList(instruction.getFields()).stream()
                            .map(field -> fieldService.getField(field))
                            .map(ResponseHelper.this::convertToFieldResponse)
                            .collect(Collectors.toList()))
                    .displayFormat(instruction.getDisplayFormat())
                    .build();
        }
    }

    public class StoryResponseHelper {
        private final Story story;
        private List<Chapter> chapters;

        public StoryResponseHelper(Story story) {
            this.story = story;
        }

        public StoryResponseHelper(Story story, List<Chapter> chapters) {
            this.story = story;
            this.chapters = chapters;
        }

        public BriefStoryResponse toBriefStoryResponse() {
            return BriefStoryResponse.builder()
                    .id(story.getId())
                    .name(story.getName())
                    .description(story.getDescription())
                    .icon(story.getIcon())
                    .build();
        }

        public StoryResponse toStoryResponse() {
            return StoryResponse.builder()
                    .id(story.getId())
                    .name(story.getName())
                    .description(story.getDescription())
                    .icon(story.getIcon())
                    .chapters(chapters.stream().map(this::toBriefChapterResponse).collect(Collectors.toList()))
                    .user(from(userService.getUser(story.getUserId())).toUserResponse())
                    .virtualAccount(story.getVirtualAccount())
                    .build();
        }

        private BriefChapterResponse toBriefChapterResponse(Chapter chapter) {
            return BriefChapterResponse.builder()
                    .id(chapter.getId())
                    .name(chapter.getName())
                    .description(chapter.getDescription())
                    .transactionPattern(chapter.getTransactionPattern())
                    .transactionType(chapter.getTransactionType())
                    .state(chapter.getState())
                    .build();
        }
    }

    public class InstructionResponseHelper {
        private final Instruction instruction;

        public InstructionResponseHelper(Instruction instruction) {
            this.instruction = instruction;
        }

        public BriefInstructionResponse toBriefInstructionResponse() {
            return BriefInstructionResponse.builder()
                    .id(instruction.getId())
                    .name(instruction.getName())
                    .description(instruction.getDescription())
                    .fields(instruction.getFields())
                    .displayFormat(instruction.getDisplayFormat())
                    .build();
        }

        public InstructionResponse toInstructionResponse() {
            return InstructionResponse.builder()
                    .id(instruction.getId())
                    .name(instruction.getName())
                    .description(instruction.getDescription())
                    .fields(ConversionHelper.convertStringToList(instruction.getFields()).stream()
                            .map(field -> fieldService.getField(field))
                            .map(ResponseHelper.this::convertToFieldResponse)
                            .collect(Collectors.toList()))
                    .displayFormat(instruction.getDisplayFormat())
                    .build();
        }
    }

    public static class UserResponseHelper {
        private final User user;

        public UserResponseHelper(User User) {
            this.user = User;
        }

        public UserResponse toUserResponse() {
            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .accessCode(user.getAccessCode())
                    .masterAccount(user.getMasterAccount())
                    .build();
        }
    }

    public static class BlueprintResponseHelper {
        private final Blueprint blueprint;
        private final List<Template> templates;

        public BlueprintResponseHelper(Blueprint blueprint, List<Template> templates) {
            this.blueprint = blueprint;
            this.templates = templates;
        }

        public BlueprintResponse toBlueprintResponse() {
            List<BriefTemplateResponse> templateResponses = templates.stream()
                    .map(this::toBriefTemplateResponse)
                    .collect(Collectors.toList());

            return BlueprintResponse.builder()
                    .id(blueprint.getId())
                    .name(blueprint.getName())
                    .shareType(blueprint.getShareType())
                    .tags(ConversionHelper.convertStringToList(blueprint.getTags()))
                    .stars(blueprint.getStars())
                    .userId(blueprint.getUserId())
                    .username(blueprint.getUsername())
                    .sharedDate(blueprint.getSharedDate())
                    .icon(blueprint.getIcon())
                    .adoptionRate(blueprint.getAdoptionRate())
                    .storyName(blueprint.getStoryName())
                    .storyDescription(blueprint.getStoryDescription())
                    .templates(templateResponses)
                    .build();
        }

        private BriefTemplateResponse toBriefTemplateResponse(Template template) {
            return BriefTemplateResponse.builder()
                    .id(template.getId())
                    .name(template.getName())
                    .description(template.getDescription())
                    .transactionPattern(template.getTransactionPattern())
                    .state(template.getState())
                    .build();
        }
    }

    public static class TemplateResponseHelper {
        private final Template template;

        public TemplateResponseHelper(Template Template) {
            this.template = Template;
        }

        public BriefTemplateResponse toBriefTemplateResponse() {
            return BriefTemplateResponse.builder()
                    .id(template.getId())
                    .name(template.getName())
                    .description(template.getDescription())
                    .transactionPattern(template.getTransactionPattern())
                    .state(template.getState())
                    .build();
        }

        public TemplateResponse toTemplateResponse() {
            return TemplateResponse.builder()
                    .id(template.getId())
                    .name(template.getName())
                    .description(template.getDescription())
                    .blueprintId(template.getBlueprintId())
                    .addOnInstructions(ConversionHelper.convertStringToInstructions(template.getAddOnInstructions()))
                    .chooseOneInstructions(ConversionHelper.convertStringToInstructions(template.getChooseOneInstructions()))
                    .requiredInstructions(ConversionHelper.convertStringToInstructions(template.getRequiredInstructions()))
                    .transactionPattern(template.getTransactionPattern())
                    .state(template.getState())
                    .build();
        }
    }

    public static class TransactionHistoryResponseHelper {
        private final List<Transaction> transactions;

        public TransactionHistoryResponseHelper(List<Transaction> transactions) {
            this.transactions = transactions;
        }

        public TransactionHistoryResponse toTransactionHistoryResponse() {
            return TransactionHistoryResponse.builder()
                    .transactionHistories(transactions.stream().collect(groupingBy(Transaction::getTransactionDate))
                            .entrySet().stream()
                            .map(entry -> TransactionHistory.builder()
                                    .transactionDate(entry.getKey())
                                    .transactionDetails(entry.getValue().stream()
                                            .map(transaction -> TransactionDetail.builder()
                                                    .id(transaction.getId())
                                                    .amount(transaction.getAmount())
                                                    .description(transaction.getDescription())
                                                    .creditType(transaction.getCreditType())
                                                    .transactionType(transaction.getTransactionType())
                                                    .storyId(transaction.getStoryId())
                                                    .chapterId(transaction.getChapterId())
                                                    .build())
                                            .collect(Collectors.toList()))
                                    .build())
                            .sorted(Comparator.comparing(TransactionHistory::getTransactionDate).reversed())
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    public static class TransactionResponseHelper {
        private final Transaction transaction;

        public TransactionResponseHelper(Transaction Transaction) {
            this.transaction = Transaction;
        }

        public TransactionResponse toTransactionResponse() {
            return TransactionResponse.builder()
                    .id(transaction.getId())
                    .fromAccount(transaction.getFromAccount())
                    .toAccount(transaction.getToAccount())
                    .amount(transaction.getAmount())
                    .description(transaction.getDescription())
                    .creditType(transaction.getCreditType())
                    .transactionDate(transaction.getTransactionDate())
                    .transactionType(transaction.getTransactionType())
                    .storyId(transaction.getStoryId())
                    .chapterId(transaction.getChapterId())
                    .build();
        }
    }

    private FieldResponse convertToFieldResponse(Field field) {
        return FieldResponse.builder()
                .fieldName(field.getFieldName())
                .displayName(field.getDisplayName())
                .dataType(field.getDataType())
                .mask(field.getMask())
                .maskedValue(field.getMaskedValue())
                .placeholder(field.getPlaceholder())
                .required(field.getRequired())
                .value(field.getValue())
                .build();
    }

    public GenreHelper from(Genre genre) {
        return new GenreHelper(genre);
    }

    public ChapterHelper from(Chapter chapter) {
        return new ChapterHelper(chapter);
    }

    public StoryResponseHelper from(Story story) {
        return new StoryResponseHelper(story);
    }

    public StoryResponseHelper from(Story story, List<Chapter> chapters) {
        return new StoryResponseHelper(story, chapters);
    }

    public UserResponseHelper from(User user) {
        return new UserResponseHelper(user);
    }

    public InstructionResponseHelper from(Instruction instruction) {
        return new InstructionResponseHelper(instruction);
    }

    public BlueprintResponseHelper from(Blueprint blueprint, List<Template> templates) {
        return new BlueprintResponseHelper(blueprint, templates);
    }

    public TemplateResponseHelper from(Template template) {
        return new TemplateResponseHelper(template);
    }

    public TransactionResponseHelper from(Transaction transaction) {
        return new TransactionResponseHelper(transaction);
    }

    public TransactionHistoryResponseHelper from(List<Transaction> transactions) {
        return new TransactionHistoryResponseHelper(transactions);
    }
}
