package com.kanban.hack.controller;

import com.kanban.hack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/v1/secured")
public class MainController {

    @Autowired
    private AuthController authController;

    @Autowired
    public void setAuthController(AuthController authController) {
        this.authController = authController;
    }

    @GetMapping("/me")
    public User userAccess(Principal principal){
        if (principal == null)
            return null;
        if (!principal.getName().equals(authController.getAuthorisedUser().getUsername())){
            return null;
        }
        return authController.getAuthorisedUser();
    }
}
