package com.example.narrative.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponse {
    private String id;
    private String name;
    private String description;
    private String icon;
    private UserResponse user;
    private List<BriefChapterResponse> chapters;
    private String virtualAccount;
}
