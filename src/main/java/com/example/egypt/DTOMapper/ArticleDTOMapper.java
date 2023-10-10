package com.example.egypt.DTOMapper;

import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.entity.Article;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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
                article.getImage(),
                article.getQuizzes(),
                article.getArchive(),
                article.getComments(),
                article.getRating());
    }

    public ArticleDTO convertToDTO(Article article) {
        return apply(article);
    }
}
