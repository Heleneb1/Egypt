package com.example.egypt.DTO;

import com.example.egypt.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record ArticleDTO(
        UUID id,
        String title,
        String content,
        LocalDateTime creationDate,
        LocalDateTime editionDate,
        String tag,
        String author,
        String image,
        @JsonIgnore List<Quiz> quizzes,
        Boolean archive,

        @JsonIgnore List<Comment> comments,
        Float ratings) {
    public LocalDateTime setEditionDate(LocalDateTime localDateTimeNow) {
        return localDateTimeNow;
    }

    public List<UUID> getQuizzesIds(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(Quiz::getId)
                .collect(Collectors.toList());
    }

    public Set<UUID> getCommentsIds(Set<Comment> comments) {
        return comments.stream()
                .map(Comment::getId)
                .collect(Collectors.toSet());
    }

//    public List<UUID> getRatingsIds(List<Rating> ratings) {
//        return ratings.stream()
//                .map(Rating::getId)
//                .collect(Collectors.toList());
//    }


}
