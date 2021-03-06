package com.example.narrative.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @Column(name = "id", length = 100)
    private String id = UUID.randomUUID().toString();

    @Column(name = "name")
    private String name;
}
