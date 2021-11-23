package com.example.narrative.controllers;

import com.example.narrative.controllers.requests.ChapterRequest;
import com.example.narrative.controllers.responses.BriefTemplateResponse;
import com.example.narrative.controllers.responses.TemplateResponse;
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
@RequestMapping("/api/v1/templates")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private RequestHelper requestHelper;

    @GetMapping("/blueprint/{blueprintId}")
    public ResponseEntity<List<BriefTemplateResponse>> getAllTemplates(@PathVariable String blueprintId) {
        return ok(templateService.getTemplates(blueprintId).stream()
                .map(template -> responseHelper.from(template).toBriefTemplateResponse()).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateResponse> updateTemplate(@RequestBody ChapterRequest templateRequest, @PathVariable UUID id) {
        return ok(responseHelper.from(templateService.update(requestHelper.from(templateRequest).toTemplate(), id))
                .toTemplateResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponse> getTemplate(@PathVariable UUID id) {
        return ok(responseHelper.from(templateService.getTemplate(id)).toTemplateResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TemplateResponse> deleteTemplate(@PathVariable UUID id) {
        return ok(responseHelper.from(templateService.delete(id)).toTemplateResponse());
    }
}
