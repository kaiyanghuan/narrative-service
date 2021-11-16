package com.example.narrative.controllers;

import com.example.narrative.controllers.requests.AppPageRequest;
import com.example.narrative.controllers.requests.BlueprintQueryRequest;
import com.example.narrative.controllers.requests.BlueprintRequest;
import com.example.narrative.controllers.responses.BlueprintResponse;
import com.example.narrative.controllers.responses.StoryResponse;
import com.example.narrative.entities.Blueprint;
import com.example.narrative.entities.Chapter;
import com.example.narrative.entities.Story;
import com.example.narrative.entities.Template;
import com.example.narrative.services.BlueprintService;
import com.example.narrative.services.ChapterService;
import com.example.narrative.services.StoryService;
import com.example.narrative.services.TemplateService;
import com.example.narrative.utils.RequestHelper;
import com.example.narrative.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/blueprints")
public class BlueprintController {

    @Autowired
    private BlueprintService blueprintService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private RequestHelper requestHelper;

    @Autowired
    private ResponseHelper responseHelper;

    @GetMapping
    public ResponseEntity<List<Blueprint>> getAllBlueprints() {
        return ok(blueprintService.getBlueprints());
    }

    @PostMapping("/{keyword}/search")
    public ResponseEntity<Page<Blueprint>> searchBlueprint(@RequestBody BlueprintQueryRequest blueprintQueryRequest) {
        AppPageRequest pageRequest = blueprintQueryRequest.getPageRequest();
        Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), pageRequest.getDirection(), pageRequest.getField());
        return ok(blueprintService.searchBlueprint(blueprintQueryRequest.getName(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlueprintResponse> getBlueprint(@PathVariable UUID id) {
        Blueprint blueprint = blueprintService.getBlueprint(id);
        List<Template> templates = templateService.getTemplates(blueprint.getId());
        return ok(responseHelper.from(blueprint, templates).toBlueprintResponse());
    }

    @PostMapping
    public ResponseEntity<BlueprintResponse> createBlueprint(@RequestBody BlueprintRequest blueprintRequest) {
        Blueprint blueprint = blueprintService.create(requestHelper.from(blueprintRequest).toBlueprint());
        List<Chapter> chapters = chapterService.getChapters(blueprintRequest.getStoryId());
        List<Template> templates = chapters.stream()
                .map(chapter -> templateService.create(chapter, blueprint.getId(), blueprintRequest.getMask()))
                .collect(Collectors.toList());
        return ok(responseHelper.from(blueprint, templates).toBlueprintResponse());
    }

    @PostMapping("/{id}/adopt")
    public ResponseEntity<StoryResponse> adoptBlueprint(@PathVariable UUID id) {
        Blueprint blueprint = blueprintService.adoptBlueprint(id);
        List<Template> templates = templateService.getTemplates(blueprint.getId());
        Story story = storyService.create(blueprint);
        List<Chapter> chapters = templates.stream()
                .map(template -> chapterService.create(template, story.getId()))
                .collect(Collectors.toList());
        return ok(responseHelper.from(story, chapters).toStoryResponse());
    }

}
