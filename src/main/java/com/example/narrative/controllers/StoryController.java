package com.example.narrative.controllers;

import com.example.narrative.controllers.requests.StoryRequest;
import com.example.narrative.controllers.responses.BriefStoryResponse;
import com.example.narrative.controllers.responses.StoryResponse;
import com.example.narrative.entities.Chapter;
import com.example.narrative.entities.Story;
import com.example.narrative.services.ChapterService;
import com.example.narrative.services.StoryService;
import com.example.narrative.utils.RequestHelper;
import com.example.narrative.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/stories")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private RequestHelper requestHelper;

    @Autowired
    private ResponseHelper responseHelper;

    @GetMapping
    public ResponseEntity<List<BriefStoryResponse>> getAllStories() {
        return ok(storyService.getStories().stream().map(
                story -> responseHelper.from(story).toBriefStoryResponse()
        ).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponse> getStory(@PathVariable UUID id) {
        return ok(generateStoryResponse(storyService.getStory(id)));
    }

    @GetMapping("/{name}/name")
    public ResponseEntity<StoryResponse> getStory(@PathVariable String name) {
        return ok(generateStoryResponse(storyService.getStory(name)));
    }

    @PostMapping()
    public ResponseEntity<StoryResponse> createStory(@RequestBody StoryRequest storyRequest) {
        return ok(generateStoryResponse(storyService.create(requestHelper.from(storyRequest).toStory())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryResponse> updateStory(@RequestBody StoryRequest storyRequest, @PathVariable UUID id) {
        return ok(generateStoryResponse(storyService.update(requestHelper.from(storyRequest).toStory(), id)));
    }

    @PutMapping("/{name}/name")
    public ResponseEntity<StoryResponse> updateStory(@RequestBody StoryRequest storyRequest, @PathVariable String name) {
        return ok(generateStoryResponse(storyService.update(requestHelper.from(storyRequest).toStory(), name)));
    }

    private StoryResponse generateStoryResponse(Story story) {
        List<Chapter> chapters = chapterService.getChapters(story.getId());
        return responseHelper.from(story, chapters).toStoryResponse();
    }
}
