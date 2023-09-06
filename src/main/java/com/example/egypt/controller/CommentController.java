package com.example.egypt.controller;


import com.example.egypt.entity.*;

import com.example.egypt.repository.ArticleRepository;
import com.example.egypt.repository.CommentRepository;

import com.example.egypt.repository.QuizRepository;
import com.example.egypt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private QuizRepository quizRepository;
    private ArticleRepository articleRepository;

    CommentController(CommentRepository commentRepository,
                      UserRepository userRepository,
                      QuizRepository quizRepository,
                      ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return this.commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getById(@PathVariable UUID id) {
        return this.commentRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{authorId}/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@PathVariable UUID authorId, @RequestBody Comment newComment) {
        User user = userRepository
                .findById(authorId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Not Found" + authorId));

        return this.commentRepository.save(newComment);
    }

    @PostMapping("/quizzes/{quizId}/comments")
    public ResponseEntity<Comment> createComment(
            @PathVariable UUID quizId,
            @RequestBody Comment newComment,
            @RequestParam UUID authorId) {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + quizId));

        User author = userRepository.findById(authorId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Author Not Found: " + authorId));

        newComment.setQuiz(quiz);
        newComment.setAuthor(author);

        Comment createdComment = commentRepository.save(newComment);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Comment update(@PathVariable UUID id, @RequestBody Comment commentUpdated) {
        commentUpdated.setId(id);
        return this.commentRepository.save(commentUpdated);
    }

    @PutMapping("/{id}/{authorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Comment update(@PathVariable UUID id,
                          @PathVariable UUID authorId,
                          @RequestBody Comment commentUpdated) {
        User user = userRepository
                .findById(authorId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Not Found" + authorId));

        commentUpdated.setId(id);
        return this.commentRepository.save(commentUpdated);
    }

    @PutMapping("/articles/{articleId}/add-comment")
    public ResponseEntity<Article> addCommentToArticle(

            @PathVariable UUID articleId,
            @RequestBody Comment newComment,
            @RequestParam UUID authorId) {

        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article Not Found: " + articleId));


        newComment.setId(null);

        newComment.setArticle(article);

        User author = userRepository.findById(authorId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Author Not Found: " + authorId));

        newComment.setAuthor(author);

        article.getComments().add(newComment);

        Article updatedArticle = articleRepository.save(article);

        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void delete(@PathVariable UUID id) {
        this.commentRepository.deleteById(id);
    }

    @DeleteMapping("/{id}/{authorId}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void deleteByAuthor(@PathVariable UUID id, @PathVariable UUID authorId) {
        User user = userRepository
                .findById(authorId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Not Found" + authorId));
        this.commentRepository.deleteById(id);
    }

}
