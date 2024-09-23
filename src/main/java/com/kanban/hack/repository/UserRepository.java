package com.kanban.hack.repository;

import com.kanban.hack.model.User;
import com.kanban.hack.viewmodel.UserVM;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
