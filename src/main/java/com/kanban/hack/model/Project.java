package com.kanban.hack.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    

    @ManyToMany(mappedBy = "assignedProjects")
    private Set<User> usersSet = new HashSet<>();
}
