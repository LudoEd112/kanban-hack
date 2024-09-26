package com.kanban.hack.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "boards")
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
}
