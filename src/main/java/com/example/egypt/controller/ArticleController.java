package com.example.egypt.controller;

import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.DTOMapper.ArticleDTOMapper;
import com.example.egypt.entity.Article;
import com.example.egypt.entity.Comment;
import com.example.egypt.entity.Quiz;
import com.example.egypt.repository.ArticleRepository;
import com.example.egypt.repository.QuizRepository;
import com.example.egypt.services.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {


    private ArticleRepository articleRepository;
    private QuizRepository quizRepository;
    private final ArticleDTOMapper articleDTOMapper;

    ArticleController(ArticleRepository articleRepository, QuizRepository quizRepository, ArticleDTOMapper articleDTOMapper) {
        this.articleRepository = articleRepository;
        this.quizRepository = quizRepository;
        this.articleDTOMapper = articleDTOMapper;
    }

    @GetMapping("")
    public List<ArticleDTO> getAllArticles() {
        ArticleService articleService = new ArticleService(
                articleRepository, articleDTOMapper, quizRepository);
        List<ArticleDTO> articleDTOS = articleService.findAllArticles();
        return articleDTOS;
    }

    @GetMapping("/{id}")
    public ArticleDTO getById(@PathVariable UUID id) {
        Article article = this.articleRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return articleDTOMapper.convertToDTO(article);
    }


    //    @GetMapping("/byAuthor/{author}")
//    public List<Article> getArticlesByAuthor(@PathVariable String author) {
//        return this.articleRepository.findArticlesByAuthorContaining(author);
//
//    }
    @GetMapping("/byAuthor/{author}")
    public List<ArticleDTO> getArticlesByAuthor(@PathVariable String author) {
        ArticleService articleService = new ArticleService(
                articleRepository, articleDTOMapper, quizRepository);
        List<ArticleDTO> articles = articleService.findByAuthor(author);
        return articles;
    }

    @GetMapping("/byQuiz/{quizId}")
    public List<ArticleDTO> getArticlesByQuiz(@PathVariable UUID quizId) {
        ArticleService articleService = new ArticleService(
                articleRepository, articleDTOMapper, quizRepository);
        List<ArticleDTO> articles = articleService.findByQuiz(quizId);
        return articles;
    }


    @PostMapping("/create")
    public Article create(@RequestBody Article newArticle) {

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        newArticle.setEditionDate(localDateTimeNow);
        return this.articleRepository.save(newArticle);
    }

    @PutMapping("/{id}")
    public Article update(@RequestBody Article articleUpdated) {
        return this.articleRepository.save(articleUpdated);
    }

    @PutMapping("/{id}/quizzes/{quizId}/add-quizzes")
    public ResponseEntity<Article> addQuizzes(
            @PathVariable UUID id,
            @PathVariable UUID quizId,
            @RequestBody @Validated Article addQuizzesArticle) {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + quizId));

        Article article = articleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article Not Found: " + id));


        article.getQuizzes().add(quiz);


        Article updatedArticle = articleRepository.save(article);

        return ResponseEntity.ok(updatedArticle);
    }

    @PostMapping("/{id}/add-comments")
    public ResponseEntity<Article> addCommentToArticle(
            @PathVariable UUID id,
            @RequestBody Comment newComment) {

        Article article = articleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article Not Found: " + id));

        // Associez le commentaire à l'article
        newComment.setArticle(article);

        // Ajoutez le commentaire à la liste des commentaires de l'article
        article.getComments().add(newComment);

        Article updatedArticle = articleRepository.save(article);

        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.articleRepository.deleteById(id);
    }

}

