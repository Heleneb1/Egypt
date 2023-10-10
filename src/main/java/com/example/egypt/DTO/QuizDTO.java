package com.example.egypt.DTO;

import com.example.egypt.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
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
                UUID author,
                UUID badge,
                @JsonIgnore List<Article> articles,
                @JsonIgnore List<Comment> comments,
                @JsonIgnore List<Rating> ratings,
                @JsonIgnore List<Question> questions) {
        public LocalDateTime setEditionDate(LocalDateTime localDateTimeNow) {
                return localDateTimeNow;
        }

        public List<UUID> getArticlesIds() {
                return articles.stream()
                                .map(Article::getId)
                                .collect(Collectors.toList());
        }

        public List<UUID> getCommentsIds() {
                return comments.stream()
                                .map(Comment::getId)
                                .collect(Collectors.toList());
        }

        public List<UUID> getRatingsIds() {
                return ratings.stream()
                                .map(Rating::getId)
                                .collect(Collectors.toList());
        }

        public List<UUID> getQuestionsIds() {
                return questions.stream()
                                .map(Question::getId)
                                .collect(Collectors.toList());
        }

        public void setAuthor(UUID authorId) {
                UUID user;
        }
}
