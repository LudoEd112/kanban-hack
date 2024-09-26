package com.kanban.hack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String title;
    @Column
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Board board;
    @ManyToOne
    private Sprint sprint;
    @ManyToOne
    private User owner;
}
