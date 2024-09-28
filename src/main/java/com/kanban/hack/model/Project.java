package com.kanban.hack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projects_id")
    private Long id;
    @Column
    private String title;
    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projects_id", referencedColumnName = "projects_id")
    private List<Task> task;

    @JsonIgnore
    @ManyToMany(mappedBy = "assignedProjects")
    private Set<User> usersSet = new HashSet<>();
}
