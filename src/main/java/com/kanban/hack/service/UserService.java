package com.kanban.hack.service;

import com.kanban.hack.model.User;
import com.kanban.hack.repository.UserRepository;
import com.kanban.hack.viewmodel.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(UserVM userVM) {
        User userToSave = new User();
        userToSave.setName(userVM.getName());
        userRepository.save(userToSave);
    }
}
