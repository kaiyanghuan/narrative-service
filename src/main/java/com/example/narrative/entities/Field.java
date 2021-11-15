package com.example.narrative.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fields")
public class Field {

    @Id
    @Column(name = "id", length = 100)
    private String id = UUID.randomUUID().toString();

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "placeholder")
    private String placeholder;

    @Column(name = "data_type")
    @Enumerated(EnumType.STRING)
    private DataType dataType;

    @Column(name = "value")
    private String value;

    @Column(name = "required")
    private Boolean required;

    @Column(name = "mask")
    private Boolean mask;

    @Column(name = "masked_value")
    private String maskedValue;

    public enum DataType {STRING, INT, DECIMAL, BOOLEAN, DATE, CHECKBOX, RADIO}
}
