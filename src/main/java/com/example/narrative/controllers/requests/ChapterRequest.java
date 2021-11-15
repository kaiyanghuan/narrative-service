package com.example.narrative.controllers.requests;

import com.example.narrative.controllers.responses.ChapterResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterRequest {
    private ChapterResponse chapter;
}
