package com.example.egypt.controller;

import com.example.egypt.entity.User;
import com.example.egypt.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable UUID id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public User create(@RequestBody User newUser) {
        return this.userRepository.save(newUser);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable UUID id, @RequestBody User userUpdated) {
        userUpdated.setId(id);
        return this.userRepository.save(userUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.userRepository.deleteById(id);
    }
}
