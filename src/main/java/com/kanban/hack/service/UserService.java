/*
package com.kanban.hack.service;

import com.kanban.hack.model.User;
import com.kanban.hack.repository.UserRepository;
import com.kanban.hack.viewmodel.UserVM;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(UserVM userVM) {
        User userToSave = new User();
        userToSave.setUsername(userVM.getUsername());
        userToSave.setPassword(userVM.getPassword());
        userToSave.setId(userVM.getId());
        userRepository.save(userToSave);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final long JWT_EXPIRATION_TIME = 86400000; // 1 день
    private static final String JWT_SECRET = "your_secret_key"; // Замените на ваш секретный ключ

    public boolean login(String username, String password) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Сравниваем  закодированный  пароль  с  паролем  из  базы  данных
        return passwordEncoder.matches(password, user.getPassword());
    }

    public String generateToken(String username) {
        //  Создаем  заголовки  JWT
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS512");

        //  Создаем  тело  JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("iat", new Date().getTime());
        claims.put("exp", new Date().getTime() + JWT_EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setHeader(headers)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
}
*/
