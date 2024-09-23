package com.kanban.kanban.service;

import com.kanban.kanban.model.User;
import com.kanban.kanban.repository.UserRepository;
import com.kanban.kanban.viewmodel.UserVM;
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
