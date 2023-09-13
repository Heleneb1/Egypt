package com.example.egypt.controller;

import com.example.egypt.entity.User;
import com.example.egypt.repository.UserRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
    private static final String AVATAR_FOLDER = "src/main/resources/static/avatars/";

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

     @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Validated User user) {
        User createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable UUID id, @RequestBody User userUpdated) {
        userUpdated.setId(id);
        return this.userRepository.save(userUpdated);
    }
    @PutMapping("/{userId}/avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(@PathVariable UUID userId, @RequestParam("avatar") MultipartFile avatar) {
        try {
            if (!Objects.requireNonNull(avatar.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            if (avatar.getSize() > 1_000_000) {
                throw new MaxUploadSizeExceededException(1_000_000);
            }

            String extension = avatar.getContentType().split("/")[1];

            byte[] bytes = avatar.getBytes();
            Path path = Paths.get(AVATAR_FOLDER + userId.toString() + "." + extension);
            Files.write(path, bytes, StandardOpenOption.CREATE);

            Map<String, String> response = new HashMap<>();
            response.put("avatarUrl", "/avatars/" + userId.toString() + "." + extension);
            this.userRepository.findById(userId).ifPresent(user -> {
                user.setAvatar(userId.toString() + "." + extension);
                this.userRepository.save(user);
            });
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while storing avatar image", e);
        }
    }

    @GetMapping("/avatar/{filename}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String filename) throws IOException {
        String avatarPath = AVATAR_FOLDER + filename;
        byte[] imageBytes = Files.readAllBytes(Paths.get(avatarPath));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.userRepository.deleteById(id);
    }
}
