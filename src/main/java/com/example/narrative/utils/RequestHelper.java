package com.example.narrative.utils;

import com.example.narrative.controllers.requests.ChapterRequest;
import com.example.narrative.controllers.requests.GenreRequest;
import com.example.narrative.controllers.requests.InstructionRequest;
import com.example.narrative.controllers.requests.StoryRequest;
import com.example.narrative.controllers.responses.ChapterResponse;
import com.example.narrative.controllers.responses.InstructionResponse;
import com.example.narrative.entities.*;
import com.example.narrative.exceptions.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestHelper {

    @Autowired
    private ObjectMapper objectMapper;

    public class GenreRequestHelper {
        private GenreRequest genreRequest;

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
        private InstructionRequest instructionRequest;

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

    public class StoryRequestHelper {
        private StoryRequest storyRequest;

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
        private ChapterRequest chapterRequest;

        public ChapterRequestHelper(ChapterRequest chapterRequest) {
            this.chapterRequest = chapterRequest;
        }

        public Chapter toChapter() {
            ChapterResponse chapter = chapterRequest.getChapter();
            return Chapter.builder()
                    .name(chapter.getName())
                    .description(chapter.getDescription())
                    .addOnInstructions(convertInstructionsToString(chapter.getAddOnInstructions()))
                    .chooseOneInstructions(convertInstructionsToString(chapter.getChooseOneInstructions()))
                    .requiredInstructions(convertInstructionsToString(chapter.getRequiredInstructions()))
                    .state(State.ACTIVE)
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
}
