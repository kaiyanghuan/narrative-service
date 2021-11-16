package com.example.narrative.entities;

import com.example.narrative.entities.enums.State;
import com.example.narrative.entities.enums.TransactionPattern;
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
@Table(name = "templates")
public class Template {

    @Id
    @Column(name = "id", length = 100)
    private String id = UUID.randomUUID().toString();

    @Column(name = "blueprint_id")
    private String blueprintId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "required_instructions", columnDefinition = "TEXT")
    private String requiredInstructions;

    @Column(name = "choose_one_instructions", columnDefinition = "TEXT")
    private String chooseOneInstructions;

    @Column(name = "add_on_instructions", columnDefinition = "TEXT")
    private String addOnInstructions;

    @Column(name = "transaction_pattern")
    @Enumerated(EnumType.STRING)
    private TransactionPattern transactionPattern;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state = State.INACTIVE;
}
