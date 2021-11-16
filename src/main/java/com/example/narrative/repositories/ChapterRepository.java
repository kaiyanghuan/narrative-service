package com.example.narrative.repositories;


import com.example.narrative.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, String> {
    List<Chapter> findByStoryId(String storyId);

    Optional<Chapter> findByName(String name);
}
