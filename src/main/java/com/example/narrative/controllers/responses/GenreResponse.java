package com.example.narrative.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreResponse {
    private String id;
    private String name;
    private String description;
    private ChapterResponse chapter;
}
