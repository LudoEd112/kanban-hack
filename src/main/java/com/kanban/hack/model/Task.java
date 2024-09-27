package com.kanban.hack.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Project project;
    @ManyToOne
    private Sprint sprint;
    @ManyToOne
    private User owner;
}
