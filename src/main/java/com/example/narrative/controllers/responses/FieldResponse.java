package com.example.narrative.controllers.responses;

import com.example.narrative.entities.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldResponse {

    private String fieldName;
    private String displayName;
    private String placeholder;
    private Field.DataType dataType;
    private String value;
    private Boolean required;
    private Boolean mask;
    private String maskedValue;
}
