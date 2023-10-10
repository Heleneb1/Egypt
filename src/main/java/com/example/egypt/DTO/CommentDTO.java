package com.example.egypt.DTO;

import com.example.egypt.entity.Article;
import com.example.egypt.entity.Quiz;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record CommentDTO(
                UUID id,
                String content,
                LocalDateTime creationDate,
                Boolean archive,
                UUID author,
                @JsonIgnore Quiz quizzes,
                Article article) {

        public List<UUID> getQuizzesIds(List<Quiz> quizzes) {
                return quizzes.stream()
                                .map(Quiz::getId)
                                .collect(Collectors.toList());
        }
}
