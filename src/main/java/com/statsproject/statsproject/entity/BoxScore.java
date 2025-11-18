package com.statsproject.statsproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoxScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Game game;

    @ManyToOne
    private Player player;

    private int points;
    private int rebounds;
    private int assists;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String createdBy; // username
}

