package com.example.narrative.repositories;

import com.example.narrative.entities.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, String> {
    List<Template> findAllByBlueprintId(String blueprintId);
}
