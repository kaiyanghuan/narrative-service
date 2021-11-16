package com.example.narrative.utils;

import com.example.narrative.controllers.requests.*;
import com.example.narrative.controllers.responses.ChapterResponse;
import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.entities.*;
import com.example.narrative.entities.enums.State;
import com.example.narrative.exceptions.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class RequestHelper {

    @Autowired
    private ObjectMapper objectMapper;

    public class GenreRequestHelper {
        private final GenreRequest genreRequest;

        public GenreRequestHelper(GenreRequest genreRequest) {
            this.genreRequest = genreRequest;
        }

        public Genre toGenre() {
            return Genre.builder()
                    .name(genreRequest.getName())
                    .description(genreRequest.getDescription())
                    .addOnInstructions(convertListToString(genreRequest.getAddOnInstructions()))
                    .chooseOneInstructions(convertListToString(genreRequest.getChooseOneInstructions()))
                    .requiredInstructions(convertListToString(genreRequest.getRequiredInstructions()))
                    .build();
        }
    }


    public class InstructionRequestHelper {
        private final InstructionRequest instructionRequest;

        public InstructionRequestHelper(InstructionRequest instructionRequest) {
            this.instructionRequest = instructionRequest;
        }

        public Instruction toInstruction() {
            return Instruction.builder()
                    .name(instructionRequest.getName())
                    .description(instructionRequest.getDescription())
                    .fields(convertListToString(instructionRequest.getFields()))
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

    public class ChapterRequestHelper {
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
                    .addOnInstructions(convertInstructionsToString(chapter.getAddOnInstructions()))
                    .chooseOneInstructions(convertInstructionsToString(chapter.getChooseOneInstructions()))
                    .requiredInstructions(convertInstructionsToString(chapter.getRequiredInstructions()))
                    .transactionPattern(chapter.getTransactionPattern())
                    .state(State.ACTIVE)
                    .build();
        }
    }

    public class BlueprintRequestHelper {
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
                    .tags(convertListToString(blueprintRequest.getTags()))
                    .shareType(blueprintRequest.getShareType())
                    .sharedDate(new Date())
                    .icon(blueprintRequest.getIcon())
                    .adoptionRate(BigInteger.ZERO)
                    .stars(4)
                    .build();
        }
    }

    private String convertListToString(List<String> stringList) {
        try {
            return objectMapper.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            throw new BusinessException("");
        }
    }

    private String convertInstructionsToString(List<InstructionResponse> instructionResponses) {
        try {
            return objectMapper.writeValueAsString(instructionResponses);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Unable to convert: " + instructionResponses.toString());
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
}
