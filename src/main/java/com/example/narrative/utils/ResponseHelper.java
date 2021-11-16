package com.example.narrative.utils;

import com.example.narrative.controllers.responses.*;
import com.example.narrative.entities.*;
import com.example.narrative.entities.enums.State;
import com.example.narrative.exceptions.ConversionException;
import com.example.narrative.services.FieldService;
import com.example.narrative.services.InstructionService;
import com.example.narrative.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseHelper {

    @Autowired
    private InstructionService instructionService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    public class ChapterHelper {
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
                    .state(chapter.getState())
                    .build();
        }

        public ChapterResponse toChapterResponse() {
            return ChapterResponse.builder()
                    .id(chapter.getId())
                    .name(chapter.getName())
                    .description(chapter.getDescription())
                    .storyId(chapter.getStoryId())
                    .addOnInstructions(convertStringToInstructions(chapter.getAddOnInstructions()))
                    .chooseOneInstructions(convertStringToInstructions(chapter.getChooseOneInstructions()))
                    .requiredInstructions(convertStringToInstructions(chapter.getRequiredInstructions()))
                    .transactionPattern(chapter.getTransactionPattern())
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
                    .build();
        }

        private List<InstructionResponse> generatingInstructionResponses(String instructions) {
            return convertStringToList(instructions).stream()
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
                    .fields(convertStringToList(instruction.getFields()).stream()
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
                    .fields(convertStringToList(instruction.getFields()).stream()
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

    public class BlueprintResponseHelper {
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
                    .tags(convertStringToList(blueprint.getTags()))
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

    public class TemplateResponseHelper {
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
                    .addOnInstructions(convertStringToInstructions(template.getAddOnInstructions()))
                    .chooseOneInstructions(convertStringToInstructions(template.getChooseOneInstructions()))
                    .requiredInstructions(convertStringToInstructions(template.getRequiredInstructions()))
                    .transactionPattern(template.getTransactionPattern())
                    .state(template.getState())
                    .build();
        }
    }

    private List<String> convertStringToList(String value) {
        try {
            return objectMapper.readValue(value, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new ConversionException("Unable to convert: " + value);
        }
    }

    private List<InstructionResponse> convertStringToInstructions(String instructions) {
        try {
            return objectMapper.readValue(instructions, new TypeReference<List<InstructionResponse>>() {
            });
        } catch (JsonProcessingException e) {
            throw new ConversionException("Unable to convert: " + instructions);
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
}
