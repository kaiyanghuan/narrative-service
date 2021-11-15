package com.example.narrative.services;

import com.example.narrative.entities.Story;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChapterService chapterService;

    public List<Story> getStories() {
        return storyRepository.findAllByUserId(userService.getUserId());
    }

    public Story getStory(UUID id) {
        return storyRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Story getStory(String name) {
        return storyRepository.findByName(name).orElseThrow(() -> new NotFoundException(name + " does not exist"));
    }

    public Story create(Story story) {
        story.setId(UUID.randomUUID().toString());
        story.setUserId(userService.getUserId());
        story.generateVirtualAccount();
        return storyRepository.save(story);
    }

    public Story update(Story otherStory, UUID id) {
        Story existingStory = getStory(id);
        updateWith(otherStory, existingStory);
        return storyRepository.save(existingStory);
    }

    public Story update(Story otherStory, String name) {
        Story existingStory = getStory(name);
        updateWith(otherStory, existingStory);
        return storyRepository.save(existingStory);
    }

    public void updateWith(Story otherStory, Story existingStory) {
        existingStory.setName(otherStory.getName());
        existingStory.setDescription(otherStory.getDescription());
        existingStory.setIcon(otherStory.getIcon());
    }
}
