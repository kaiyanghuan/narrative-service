package com.example.narrative.controllers;

import com.example.narrative.controllers.requests.TagRequest;
import com.example.narrative.entities.Tag;
import com.example.narrative.services.TagService;
import com.example.narrative.utils.RequestHelper;
import com.example.narrative.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private RequestHelper requestHelper;

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ok(tagService.getTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable UUID id) {
        return ok(tagService.getTag(id));
    }

    @PostMapping()
    public ResponseEntity<Tag> createTag(
            @RequestBody TagRequest tagRequest) {
        return ok(tagService.create(tagRequest.getTag()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable UUID id) {
        return ok(tagService.delete(id));
    }
}
