package com.kanban.hack.model;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")//незаметно добрались до hibernate,
// здесь указывается что id будет автоматически увеличиваться при новых записях
    @GeneratedValue(generator = "generator", strategy = GenerationType.IDENTITY)//аннотация генерации id
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
}
