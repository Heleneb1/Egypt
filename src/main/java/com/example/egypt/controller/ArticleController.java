package com.example.egypt.controller;

import com.example.egypt.entity.Article;
import com.example.egypt.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<String> createArticle(@RequestBody Article article) {
        try {
            // Enregistrez l'article dans la base de données en utilisant le repository
            articleRepository.save(article);
            return new ResponseEntity<>("Article créé avec succès", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de l'article : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

