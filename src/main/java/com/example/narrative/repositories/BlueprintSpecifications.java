package com.example.narrative.repositories;

import com.example.narrative.entities.Blueprint;
import org.apache.catalina.Group;

import javax.persistence.criteria.Path;
import java.util.List;

public class BlueprintSpecifications {

    public static Specification<Blueprint> searchKeyword(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
    }

    public static Specification<Blueprint> searchTags(List<Long> tags) {
        return (root, query, builder) -> {
            final Path<Group> blueprintTags = root.<Group>get("tags");
            return blueprintTags.in(tags);
        };
    }
}
