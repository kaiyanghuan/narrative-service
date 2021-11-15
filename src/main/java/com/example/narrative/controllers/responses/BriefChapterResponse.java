package com.example.narrative.controllers.responses;

import com.example.narrative.entities.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BriefChapterResponse {
    private String id;
    private String name;
    private String description;
    private State state;
}