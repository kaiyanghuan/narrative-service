package com.example.narrative.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppPageRequest {
    private int page;
    private int size;
    private Sort.Direction direction;
    private String field;
}
