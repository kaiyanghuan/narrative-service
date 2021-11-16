package com.example.narrative.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlueprintQueryRequest {
    private String name;
    private List<String> tags;
    private AppPageRequest pageRequest;
}
