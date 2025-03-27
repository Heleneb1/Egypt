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
import org.springframework.http.ResponseEntity;
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
        System.out.println("Tentative de connexion pour : " + user.getEmail());

        if (checkCookieToken(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already logged in.");
        }

        Authentication authentication = this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        var jwt = tokenService.generateToken(authentication);
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied");
        }
        // Ajouter le JWT dans un cookie HTTP-Only sécurisé
        Cookie cookie = new Cookie("auth_token", jwt);
        cookie.setHttpOnly(true); // Empêche l'accès au cookie via JavaScript

        // Activer Secure seulement en production
        boolean isProduction = request.getServerName().equals("new-app.lesmysteresdelegypteantique.fr");
        System.out.println("En prod " + isProduction);
        cookie.setSecure(isProduction);
        cookie.setPath("/"); // Le cookie est accessible sur toute l'application
        cookie.setMaxAge(2 * 60 * 60); // Durée d'expiration du cookie (2 heures par exemple)
        cookie.setAttribute("SameSite", "None"); // Cross-origin autorisé
        // // Retourner l'utilisateur
        // return (User) authentication.getPrincipal();
        response.setHeader("Authorization", "Bearer " + jwt);
        response.addCookie(cookie);
        System.out.println("Connexion réussie");
        return (User) authentication.getPrincipal();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth_token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // à mettre sur true en prod
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expire immédiatement
        response.addCookie(cookie);
        System.out.println("Déconnexion, suppression du cookie...");

        return ResponseEntity.ok().build();
    }

}
