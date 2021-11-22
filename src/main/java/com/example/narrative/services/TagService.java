package com.example.narrative.services;

import com.example.narrative.entities.Tag;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public Tag getTag(UUID id) {
        return tagRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Tag create(String newTag) {
        return tagRepository.save(Tag.builder()
                .name(newTag)
                .id(UUID.randomUUID().toString())
                .build());
    }

    public Tag delete(UUID id) {
        Tag tag = getTag(id);
        tagRepository.delete(tag);
        return tag;
    }
}
