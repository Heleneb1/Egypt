package com.example.egypt.DTOMapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.entity.Article;
import com.example.egypt.entity.Rating;

@Service
public class ArticleDTOMapper implements Function<Article, ArticleDTO> {

    @Override
    public ArticleDTO apply(Article article) {
        // Calcul de la note moyenne
        Float averageRating = article.getRatings().isEmpty()
                ? 3.5f // Valeur par défaut si pas de notes
                : (float) article.getRatings().stream()
                        .mapToDouble(Rating::getRating)
                        .average()
                        .orElse(3.5);

        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreationDate(),
                article.getEditionDate(),
                article.getTag(),
                article.getAuthor(),
                article.getImage(),
                article.getSlug(),
                article.getQuizzes(),
                article.getArchive(),
                averageRating,
                article.getComments(),
                article.getRatings()

        );
    }

    public ArticleDTO convertToDTO(Article article) {
        return apply(article);
    }

}