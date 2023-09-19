package com.example.egypt.DTO;

import com.example.egypt.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record QuizDTO(
        UUID id,
        String content,
        String title,
        String difficulty,
        LocalDateTime creationDate,
        String article,
        String picture,
        Float rating,
        Boolean archive,
        User author,
        @JsonIgnore List<Badge>badge,
        @JsonIgnore List<Article> articles,
        @JsonIgnore List<Comment> comments
) {
    public LocalDateTime setEditionDate(LocalDateTime localDateTimeNow) {
        return localDateTimeNow;
    }


    public List<UUID> getBadgesIds(List<Badge> badge) {
        return badge.stream()
                .map(Badge::getId)
                .collect(Collectors.toList());
    }

    public Set<UUID> getCommentsIds(Set<Comment> comments) {
        return comments.stream()
                .map(Comment::getId)
                .collect(Collectors.toSet());
    }

    public List<UUID> getRatingsIds(List<Rating> ratings) {
        return ratings.stream()
                .map(Rating::getId)
                .collect(Collectors.toList());
    }

    public void setAuthor(UUID authorId) {
        UUID user;
    }
}
