package com.example.narrative.services;

import com.example.narrative.entities.Chapter;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.ChapterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChapterService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChapterRepository chapterRepository;

    public List<Chapter> getChapters(String storyId) {
        return chapterRepository.findByStoryId(storyId);
    }

    public Chapter getChapter(UUID id) {
        return chapterRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Chapter create(Chapter chapter, String storyId) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setStoryId(storyId);
        return chapterRepository.save(chapter);
    }

    public Chapter update(Chapter otherChapter, UUID id) {
        Chapter existingChapter = getChapter(id);
        updateWith(otherChapter, existingChapter);
        return chapterRepository.save(existingChapter);
    }

    public void updateWith(Chapter otherChapter, Chapter existingChapter) {
        existingChapter.setName(otherChapter.getName());
        existingChapter.setDescription(otherChapter.getDescription());
        existingChapter.setAddOnInstructions(otherChapter.getAddOnInstructions());
        existingChapter.setRequiredInstructions(otherChapter.getRequiredInstructions());
        existingChapter.setChooseOneInstructions(otherChapter.getChooseOneInstructions());
        existingChapter.setState(otherChapter.getState());
    }
}