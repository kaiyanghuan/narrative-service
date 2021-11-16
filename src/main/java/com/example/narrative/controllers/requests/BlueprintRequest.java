package com.example.narrative.controllers.requests;

import com.example.narrative.entities.Blueprint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlueprintRequest {
    private String name;
    private Blueprint.ShareType shareType;
    private String storyId;
    private String storyName;
    private String storyDescription;
    private String icon;
    private List<String> tags;
    private Boolean mask;
}
