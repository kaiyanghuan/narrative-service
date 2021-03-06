package com.example.narrative.controllers;

import com.example.narrative.controllers.requests.BlueprintQueryRequest;
import com.example.narrative.controllers.requests.BlueprintRequest;
import com.example.narrative.controllers.responses.BlueprintResponse;
import com.example.narrative.controllers.responses.BriefBlueprintResponse;
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
    public ResponseEntity<List<BriefBlueprintResponse>> getAllBlueprints() {
        return ok(blueprintService.getBlueprints().stream().map(blueprint ->
                responseHelper.from(blueprint).toBriefBlueprintResponse()).collect(Collectors.toList()));
    }

    @PostMapping("/search")
    public ResponseEntity<List<BriefBlueprintResponse>> searchBlueprint(@RequestBody BlueprintQueryRequest blueprintQueryRequest) {
        return ok(blueprintService.searchBlueprint(blueprintQueryRequest).stream().map(blueprint ->
                responseHelper.from(blueprint).toBriefBlueprintResponse()).collect(Collectors.toList()));
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
                .map(chapter -> templateService.create(chapter, blueprint, blueprintRequest.getMask()))
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

    @DeleteMapping("/{id}")
    public ResponseEntity<BlueprintResponse> deleteBlueprint(@PathVariable UUID id) {
        List<Template> templates = templateService.deleteAllInBlueprint(id);
        return ok(responseHelper.from(blueprintService.delete(id), templates).toBlueprintResponse());
    }
}
