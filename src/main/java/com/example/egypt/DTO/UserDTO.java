package com.example.egypt.DTO;

//import com.example.egypt.config.GrantedAuthoritySerializer;
import com.example.egypt.entity.Badge;
import com.example.egypt.entity.Comment;
import com.example.egypt.entity.Quiz;
import com.example.egypt.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserDTO(UUID id,
        String avatar,
        String biography,
        String lastname,
        String firstname,
        Role role,
        String email,
        @JsonIgnore List<Comment> comments,
        @JsonIgnore List<Quiz> quiz,
        @JsonIgnore Set<Badge> badge) {
    public List<UUID> getCommentsIds() {
        return comments.stream()
                .map(Comment::getId)
                .collect(Collectors.toList());
    }

    public List<UUID> getQuizzesIds() {
        return quiz.stream()
                .map(Quiz::getId)
                .collect(Collectors.toList());
    }

    public Set<UUID> getBadgesIds() {
        return badge.stream()
                .map(Badge::getId)
                .collect(Collectors.toSet());
    }

}
