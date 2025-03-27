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
        String slug,
        @JsonIgnore List<Quiz> quizzes,
        Boolean archive,
        Float averageRating, // Ajout de la moyenne
        List<Comment> comments,
        @JsonIgnore List<Rating> ratings) {
            
    // Méthode pour obtenir les ids des quiz associés
    public List<UUID> getQuizzesIds(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(Quiz::getId)
                .collect(Collectors.toList());
    }

    // Méthode pour obtenir les ids des commentaires associés
    public Set<UUID> getCommentsIds(Set<Comment> comments) {
        return comments.stream()
                .map(Comment::getId)
                .collect(Collectors.toSet());
    }

    // Méthode pour obtenir les ids des notes
    public List<UUID> getRatingsIds(List<Rating> ratings) {
        return ratings.stream()
                .map(Rating::getId)
                .collect(Collectors.toList());
    }
}
