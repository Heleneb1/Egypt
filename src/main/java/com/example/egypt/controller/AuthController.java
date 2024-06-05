package com.example.egypt.controller;

import com.example.egypt.entity.Role;
import com.example.egypt.entity.User;
import com.example.egypt.repository.UserRepository;
import com.example.egypt.services.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public AuthController(
            UserRepository userRepositoryInjected,
            AuthenticationManager authManagerInjected,
            TokenService tokenServiceInjected) {
        this.userRepository = userRepositoryInjected;
        this.authManager = authManagerInjected;
        this.tokenService = tokenServiceInjected;
    }

    private boolean checkCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth_token")) {
                    return true;
                }
            }
        }
        return false;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody User user, HttpServletRequest request) {
        if (checkCookieToken(request)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must logout before registering");
        }

        user.setRole(Role.USER);
        user.setAccepted(user.getAccepted());
        user.setAccepted(user.getAccepted());
        System.out.println(user.getAccepted());

        if (!user.getAccepted()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must accept the GCU");
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {
        if (checkCookieToken(request)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must logout before registering");
        }
        Authentication authentication = this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        var jwt = tokenService.generateToken(authentication);
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied");
        }

        // Ajouter le token dans la réponse
        response.setHeader("Authorization", "Bearer " + jwt);

        return (User) authentication.getPrincipal();
    }

}
