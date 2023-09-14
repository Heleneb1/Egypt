package com.example.egypt.repository;

import com.example.egypt.entity.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    List<Article> findArticlesByAuthorContaining(String author);

    List<Article> findArticleByTitleOrAuthorOrTagIsContainingIgnoreCase(String title, String author, String tag);

    List<Article> findArticlesByTagContainingIgnoreCase(String tag);

    List<Article> findByTitleContainingIgnoreCase(String title);

    List<Article> findByQuizzesId(UUID quizId);


}
