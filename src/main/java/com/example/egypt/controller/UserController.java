package com.example.egypt.controller;

import com.example.egypt.DTO.BadgeDTO;
import com.example.egypt.DTO.UserDTO;
import com.example.egypt.DTOMapper.UserDTOMapper;
import com.example.egypt.entity.Badge;
import com.example.egypt.entity.User;
import com.example.egypt.repository.BadgeRepository;
import com.example.egypt.repository.UserRepository;

import com.example.egypt.services.BeanUtils;
import com.example.egypt.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private BadgeRepository badgeRepository;
    private static final String AVATAR_FOLDER = "src/main/resources/static/avatars/";

    UserController(UserRepository userRepository,
                   UserDTOMapper userDTOMapper,
                   BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.badgeRepository= badgeRepository;
    }

//    @GetMapping("/{id}")
//    public UserDTO getUserById(@PathVariable UUID id) {
//        Optional<UserDTO> optionalUserDTO = UserService.findUserById(id);
//        if (optionalUserDTO.isPresent()) {
//            return optionalUserDTO.get();
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found " + id);
//        }
//    }

    @GetMapping("/{id}")
    public User getById(@PathVariable UUID id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        UserService userService = new UserService(userRepository, userDTOMapper);
        List<UserDTO> userDTOs = userService.findAllUsers();
        return userDTOs;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Validated User user) {
        User createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    //    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id,
//                                              @RequestBody @Validated User userDTO) {
//        User updatedUser = userRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Promotion not found: " + id));
//
//        BeanUtils.copyNonNullProperties(userDTO, updatedUser);
//        User savedUser = userRepository.save(updatedUser);
//
//        UserDTO updatedPromotionDTO = userDTOMapper.convertToDTO(savedUser);
//        return ResponseEntity.ok(updatedPromotionDTO);
//
//    }
    @PutMapping("/{userId}/badges/{badgeId}")
    public ResponseEntity<UserDTO> awardBadgeToUser(
            @PathVariable UUID userId,
            @PathVariable UUID badgeId,
            @RequestBody Badge badgeDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found: " + userId));
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Badge not found: " + userId));


        // Créez un badge avec les détails requis
//        Badge badge = new Badge();
//        badge.setName(badge.getName());
//        badge.setDescription(badge.getDescription());
        // Vous pouvez également définir d'autres propriétés du badge ici

        // Ajoutez le badge à la collection de badges de l'utilisateur
        user.getBadges().add(badge);

        // Enregistrez l'utilisateur mis à jour en base de données
        User updatedUser = userRepository.save(user);

        // Convertissez l'utilisateur en DTO si nécessaire
        UserDTO userDTO = userDTOMapper.convertToDTO(updatedUser);

        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id,
                                              @RequestBody @Validated User userDTO) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found: " + id));

        BeanUtils.copyNonNullProperties(userDTO, updatedUser);
        User savedUser = userRepository.save(updatedUser);

        UserDTO updatedUserDTO = userDTOMapper.convertToDTO(savedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    //Todo revoir

    @PutMapping("/{userId}/update-bio")
    public ResponseEntity<Map<String, String>> updateBio(@RequestBody String newBio, @PathVariable UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found: " + userId));
        if (user != null) {
            user.setBiography(newBio);
            userRepository.save(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Bio mise à jour avec succès !");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Vous devez être connecté pour modifier votre biographie.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
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
