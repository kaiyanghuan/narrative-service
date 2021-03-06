package com.example.narrative.services;

import com.example.narrative.entities.Chapter;
import com.example.narrative.entities.Template;
import com.example.narrative.entities.enums.State;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    public List<Chapter> getChapters(String storyId) {
        return chapterRepository.findByStoryId(storyId);
    }

    public Chapter getChapter(UUID id) {
        return chapterRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Optional<Chapter> findChapter(UUID id) {
        return chapterRepository.findById(id.toString());
    }

    public Chapter create(Chapter chapter, String storyId) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setStoryId(storyId);
        return chapterRepository.save(chapter);
    }

    public Chapter create(Template template, String storyId) {
        Chapter chapter = Chapter.builder()
                .id(UUID.randomUUID().toString())
                .name(template.getName())
                .description(template.getDescription())
                .storyId(storyId)
                .addOnInstructions(template.getAddOnInstructions())
                .chooseOneInstructions(template.getChooseOneInstructions())
                .requiredInstructions(template.getRequiredInstructions())
                .transactionType(template.getTransactionType())
                .transactionPattern(template.getTransactionPattern())
                .state(template.getState())
                .build();
        chapter.setId(UUID.randomUUID().toString());
        chapter.setStoryId(storyId);
        return chapterRepository.save(chapter);
    }

    public Chapter move(UUID id, String storyId) {
        Chapter chapter = getChapter(id);
        chapter.setStoryId(storyId);
        return chapterRepository.save(chapter);
    }

    public Chapter copy(UUID id, String storyId) {
        Chapter existingChapter = getChapter(id);
        Chapter chapter = Chapter.builder()
                .id(UUID.randomUUID().toString())
                .name(existingChapter.getName())
                .description(existingChapter.getDescription())
                .storyId(storyId)
                .addOnInstructions(existingChapter.getAddOnInstructions())
                .chooseOneInstructions(existingChapter.getChooseOneInstructions())
                .requiredInstructions(existingChapter.getRequiredInstructions())
                .transactionPattern(existingChapter.getTransactionPattern())
                .state(State.INACTIVE)
                .build();
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
        existingChapter.setTransactionPattern(otherChapter.getTransactionPattern());
        existingChapter.setTransactionType(otherChapter.getTransactionType());
        existingChapter.setState(otherChapter.getState());
    }

    public List<Chapter> deleteAllInStory(UUID storyId) {
        List<Chapter> chapters = getChapters(storyId.toString());
        chapters.forEach(chapter -> chapterRepository.delete(chapter));
        return chapters;
    }

    public Chapter delete(UUID id) {
        Chapter chapter = getChapter(id);
        chapterRepository.delete(chapter);
        return chapter;
    }
}