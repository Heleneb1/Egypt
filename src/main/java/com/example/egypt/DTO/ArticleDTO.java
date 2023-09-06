package com.example.egypt.DTO;

import com.example.egypt.entity.Comment;
import com.example.egypt.entity.Quiz;
import com.example.egypt.entity.Rating;
import com.example.egypt.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ArticleDTO(
        UUID id,
        String title,
        String content,
        LocalDateTime creationDate,
        LocalDateTime editionDate,
        String tag,
        User author,
        List<Quiz> quizzes,
        boolean archive,
        List<Comment> comments,
        List<Rating> ratings
) {
}
