package com.kanban.hack.controller;

import com.kanban.hack.service.UserService;
import com.kanban.hack.viewmodel.UserVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    @Autowired
    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /*@PostMapping("/register")
    public String register(@RequestBody UserVM userVM) {

        userVM.setPassword(passwordEncoder.encode(userVM.getPassword()));
        userService.create(userVM);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }*/

    @PostMapping("/register")
    @Operation(summary = "Registration", description = "Registration new User")
    public ResponseEntity<String> register(@RequestBody UserVM userVM) {
        try {
            //  Закодируйте  пароль  перед  сохранением  в  базу
            userVM.setPassword(passwordEncoder.encode(userVM.getPassword()));
            //  Сохраните  пользователя
            userService.create(userVM);
            //  Возвратите  успешный  отклик
            return ResponseEntity.ok("Пользователь успешно зарегистрирован");
        } catch (Exception e) {
            //  Обработайте  ошибки  и  верните  соответствующий  отклик
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка регистрации");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login User")
    public ResponseEntity<String> login(@RequestBody UserVM userVM) {
        try {
            //  Проверьте  логин  и  пароль
            if (userService.login(userVM.getUsername(), userVM.getPassword())) {
                //  Генерируйте  JWT  токен  и  верните  его  в  отклике
                String jwtToken = userService.generateToken(userVM.getUsername());
                return ResponseEntity.ok(jwtToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверные  логин  или  пароль");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка  входа  в  систему");
        }
    }
}
