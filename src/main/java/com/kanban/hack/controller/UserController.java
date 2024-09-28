package com.kanban.hack.controller;

import com.kanban.hack.model.User;
import com.kanban.hack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "Users", description = "Users API")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{userId}/projects/{projectId}")
    @Operation(summary = "Assign project", description = "assign project to user")
    public User assignProjectToUser(@PathVariable Long userId, @PathVariable Long projectId){
        return userService.assignProjectToUser(userId, projectId);
    }
}
