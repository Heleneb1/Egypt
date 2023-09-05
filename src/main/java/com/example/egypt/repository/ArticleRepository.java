package com.example.egypt.repository;

import com.example.egypt.entity.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
