package com.example.narrative.controllers.responses;

import com.example.narrative.entities.Blueprint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BriefBlueprintResponse {
    private String id;
    private String name;
    private Blueprint.ShareType shareType;
    private List<String> tags;
    private Integer stars;
    private String userId;
    private String username;
    private String sharedDate;
    private String icon;
    private BigInteger adoptionRate;
    private String storyName;
    private String storyDescription;
}
