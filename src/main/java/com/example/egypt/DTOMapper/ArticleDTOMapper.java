package com.example.egypt.DTOMapper;

import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.entity.Article;
import com.example.egypt.entity.Comment;
import com.example.egypt.entity.Quiz;
import com.example.egypt.entity.Rating;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleDTOMapper implements Function<Article, ArticleDTO> {

    @Override
    public ArticleDTO apply(Article article) {


        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreationDate(),
                article.getEditionDate(),
                article.getTag(),
                article.getAuthor(),
                article.getQuizzes(),
                article.getArchive(),
                article.getComments(),
                article.getRatings()
        );
    }

    public LocalDateTime setEditionDate(LocalDateTime localDateTimeNow) {
        return localDateTimeNow;
    }

    public ArticleDTO convertToDTO(Article article) {
        return apply(article);
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

    public List<UUID> getRatingsIds(List<Rating> ratings) {
        return ratings.stream()
                .map(Rating::getId)
                .collect(Collectors.toList());
    }
}
