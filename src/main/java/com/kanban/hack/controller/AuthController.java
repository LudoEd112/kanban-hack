package com.kanban.hack.controller;

import com.kanban.hack.JwtCore;
import com.kanban.hack.model.User;
import com.kanban.hack.repository.UserRepository;
import com.kanban.hack.service.UserServiceCustom;
import com.kanban.hack.viewmodel.LoginUser;
import com.kanban.hack.viewmodel.UserVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    @Autowired
    private UserServiceCustom userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;

    /*public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }*/

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtCore(JwtCore jwtCore){
        this.jwtCore = jwtCore;
    }
    @Autowired
    public void setUserService(UserServiceCustom userService){
        this.userService = userService;
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
    public ResponseEntity<?> register(@RequestBody UserVM userVM) {
        if (userRepository.existsByUsername(userVM.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
        }
        if (userRepository.existsByEmail(userVM.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different email");
        }

        String hashed = passwordEncoder.encode(userVM.getPassword());

        User user = new User();
        user.setId(userVM.getId());
        user.setUsername(userVM.getUsername());
        user.setEmail(userVM.getEmail());
        user.setPassword(hashed);
        userService.save(user);
        return ResponseEntity.ok("Success");

        /*try {
            //  Закодируйте  пароль  перед  сохранением  в  базу
            userVM.setPassword(passwordEncoder.encode(userVM.getPassword()));
            //  Сохраните  пользователя
            userService.create(userVM);
            //  Возвратите  успешный  отклик
            return ResponseEntity.ok("Пользователь успешно зарегистрирован");
        } catch (Exception e) {
            //  Обработайте  ошибки  и  верните  соответствующий  отклик
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка регистрации");
        }*/
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login User")
    public ResponseEntity<String> login(@RequestBody LoginUser loginUser) {
        Authentication authentication = null;
        try {
            System.out.println("Username: " + loginUser.getUsername());
            System.out.println("Password: " + loginUser.getPassword());
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(jwt);
       /* try {
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
        }*/
    }
}
