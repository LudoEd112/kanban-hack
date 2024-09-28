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
    private Long projectId;
    @Column
    private Long creatorId;
    @Column
    private String title;
    @Column
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;

   /* @ManyToOne
    @JoinColumn(name = "projects_id")
    public Project project;*/
    /*@Column
    private LocalDate date;*/
    /*@ManyToOne
    private Project project;
    @ManyToOne
    private Sprint sprint;
    @ManyToOne
    private User owner;*/
}
