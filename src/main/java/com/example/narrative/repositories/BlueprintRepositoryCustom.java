package com.example.narrative.repositories;

import com.example.narrative.controllers.requests.BlueprintQueryRequest;
import com.example.narrative.entities.Blueprint;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlueprintRepositoryCustom {
    List<Blueprint> findAllByBlueprintQueryRequest(BlueprintQueryRequest blueprintQueryRequest);

    Page<Blueprint> findAllPageByBlueprintQueryRequest(BlueprintQueryRequest blueprintQueryRequest);
}
