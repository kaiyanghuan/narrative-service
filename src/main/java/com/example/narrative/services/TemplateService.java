package com.example.narrative.services;

import com.example.narrative.entities.Chapter;
import com.example.narrative.entities.Template;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.TemplateRepository;
import com.example.narrative.utils.TemplateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateHelper templateHelper;

    public List<Template> getTemplates(String blueprintId) {
        return templateRepository.findAllByBlueprintId(blueprintId);
    }

    public Template getTemplate(UUID id) {
        return templateRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Template create(Chapter chapter, String blueprintId, Boolean mask) {
        return templateRepository.save(templateHelper.convertStoryToTemplate(chapter, blueprintId, mask));
    }

    public List<Template> deleteAllInBlueprint(UUID blueprintId) {
        List<Template> templates = getTemplates(blueprintId.toString());
        templates.forEach(template -> templateRepository.delete(template));
        return templates;
    }

    public Template delete(UUID id) {
        Template template = getTemplate(id);
        templateRepository.delete(template);
        return template;
    }
}