package com.kanban.hack.controller;

import com.kanban.hack.JwtCore;
import com.kanban.hack.JwtResponse;
import com.kanban.hack.RefreshJwtRequest;
import com.kanban.hack.UserDetailsImpl;
import com.kanban.hack.exception.UnauthorizedException;
import com.kanban.hack.model.User;
import com.kanban.hack.repository.UserRepository;
import com.kanban.hack.service.UserService;
import com.kanban.hack.viewmodel.LoginUser;
import com.kanban.hack.viewmodel.UserVM;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    @Autowired
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;

    private final Map<String, String> refreshStorage = new HashMap<>();

    private User authorisedUser = new User();

    @Autowired
    public User getAuthorisedUser() {
        return authorisedUser;
    }

    @Autowired
    public void setAuthorisedUser(User authorisedUser) {
        this.authorisedUser = authorisedUser;
    }

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
    public void setUserService(UserService userService){
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
    public JwtResponse login(@RequestBody LoginUser loginUser) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));

        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("unauthorized message");
        }
        User user = new User();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        user.setId(userDetails.getId());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        setAuthorisedUser(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateAccessToken(user);
        String jwtRefresh = jwtCore.generateRefreshToken(user);
        refreshStorage.put(loginUser.getUsername(), jwtRefresh);
        return new JwtResponse(jwt, jwtRefresh);

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

    @PostMapping("/refresh")
    @Operation(summary = "Refresh", description = "refresh token")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtCore.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtCore.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(username);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = new User();
                if (userService.loadUserByUsername(username) == null){
                    throw new AuthException("Пользователь не найден");
                }
                user.setUsername(userService.loadUserByUsername(username).getUsername());
                user.setPassword(userService.loadUserByUsername(username).getPassword());
                final String jwt = jwtCore.generateAccessToken(user);
                final String newJwtRefresh = jwtCore.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newJwtRefresh);
                return new JwtResponse(jwt, newJwtRefresh);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    @PostMapping("/token")
    @Operation(summary = "Token", description = "Get new access token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtCore.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtCore.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(username);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = new User();
                if (userService.loadUserByUsername(username) == null){
                    throw new AuthException("Пользователь не найден");
                }
                user.setUsername(userService.loadUserByUsername(username).getUsername());
                user.setPassword(userService.loadUserByUsername(username).getPassword());
                final String accessToken = jwtCore.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }
}
