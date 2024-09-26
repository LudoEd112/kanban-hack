package com.kanban.hack.viewmodel;

import lombok.Data;

@Data
public class UserVM {

    private Long id;
    private String username;
    private String password;
    private String email;
}
