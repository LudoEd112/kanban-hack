package com.kanban.hack.controller;

import com.kanban.hack.model.User;
import com.kanban.hack.service.UserService;
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
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthController(AuthController authController) {
        this.authController = authController;
    }

    @GetMapping("/me")
    public User userAccess(Principal principal){
        User userFromLogin = authController.getAuthorisedUser();
        if (principal == null)
            return null;
        return userService.securedMe(principal);
    }


}
