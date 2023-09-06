package com.example.egypt.DTO;

import com.example.egypt.entity.Article;

import com.example.egypt.entity.Quiz;

import java.time.LocalDateTime;

import java.util.UUID;

public record CommentDTO(
        UUID id,
        String content,
        LocalDateTime creationDate,
        Boolean archive,
        UUID author,
        Quiz quiz,
        Article article) {
}
