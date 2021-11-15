package com.example.narrative.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blueprints")
public class Blueprint {

    @Id
    @Column(name = "id", length = 100)
    private String id = UUID.randomUUID().toString();

    @Column(name = "name")
    private String name;

    @Column(name = "share_type")
    @Enumerated(EnumType.STRING)
    private ShareType shareType;

    @Column(name = "story")
    private String story;

    @Column(name = "tags")
    private String tags;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "shared_date")
    private Date sharedDate;

    @Column(name = "adoption_rate")
    private BigInteger adoptionRate;

    public enum ShareType {PRIVATE, PUBLIC}
}
