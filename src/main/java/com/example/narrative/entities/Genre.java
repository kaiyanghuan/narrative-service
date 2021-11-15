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
@Table(name = "genre")
public class Genre {

    @Id
    @Column(name = "id", length = 100)
    private String id = UUID.randomUUID().toString();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "required_instructions")
    private String requiredInstructions;

    @Column(name = "choose_one_instructions")
    private String chooseOneInstructions;

    @Column(name = "add_on_instructions")
    private String addOnInstructions;
}
