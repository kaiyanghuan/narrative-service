package com.example.narrative.controllers;

import com.example.narrative.controllers.requests.ChapterRequest;
import com.example.narrative.controllers.responses.BriefChapterResponse;
import com.example.narrative.controllers.responses.ChapterResponse;
import com.example.narrative.services.ChapterService;
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
@RequestMapping("/api/v1/chapters")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private RequestHelper requestHelper;

    @GetMapping("/story/{storyId}")
    public ResponseEntity<List<BriefChapterResponse>> getAllChapters(@PathVariable String storyId) {
        return ok(chapterService.getChapters(storyId).stream()
                .map(chapter -> responseHelper.from(chapter).toBriefChapterResponse()).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapterResponse> getChapter(@PathVariable UUID id) {
        return ok(responseHelper.from(chapterService.getChapter(id)).toChapterResponse());
    }

    @PostMapping("/story/{storyId}")
    public ResponseEntity<ChapterResponse> createChapter(@RequestBody ChapterRequest chapterRequest, @PathVariable String storyId) {
        return ok(responseHelper.from(chapterService.create(requestHelper.from(chapterRequest).toChapter(), storyId))
                .toChapterResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChapterResponse> updateChapter(@RequestBody ChapterRequest chapterRequest, @PathVariable UUID id) {
        return ok(responseHelper.from(chapterService.update(requestHelper.from(chapterRequest).toChapter(), id))
                .toChapterResponse());
    }
}
