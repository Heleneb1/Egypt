package com.example.egypt.controller;

import com.example.egypt.DTO.CommentDTO;
import com.example.egypt.DTOMapper.CommentDTOMapper;
import com.example.egypt.entity.*;

import com.example.egypt.repository.ArticleRepository;
import com.example.egypt.repository.CommentRepository;

import com.example.egypt.repository.QuizRepository;
import com.example.egypt.repository.UserRepository;
import com.example.egypt.services.BeanUtils;
import com.example.egypt.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {
        private CommentRepository commentRepository;
        private UserRepository userRepository;
        private QuizRepository quizRepository;
        private ArticleRepository articleRepository;
        private final CommentDTOMapper commentDTOMapper;

        CommentController(CommentRepository commentRepository,
                        UserRepository userRepository,
                        QuizRepository quizRepository,
                        ArticleRepository articleRepository, CommentDTOMapper commentDTOMapper) {
                this.commentRepository = commentRepository;
                this.userRepository = userRepository;
                this.quizRepository = quizRepository;
                this.articleRepository = articleRepository;

                this.commentDTOMapper = commentDTOMapper;
        }

        @GetMapping
        public List<CommentDTO> getAllComments() {
                CommentService commentService = new CommentService(
                                commentRepository, commentDTOMapper);
                List<CommentDTO> commentDTOS = commentService.findAll();
                return commentDTOS;
        }

        @GetMapping("/{id}")
        public CommentDTO getById(@PathVariable UUID id) {
                Comment comment = this.commentRepository
                                .findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                return commentDTOMapper.convertToDTO(comment);
        }

        @GetMapping("/byArticle/{articleId}")
        public List<CommentDTO> getByArticle(@PathVariable UUID articleId) {
                Article article = this.articleRepository.findById(articleId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                List<Comment> comments = this.commentRepository.findByArticle(article);

                List<CommentDTO> commentDTOs = comments.stream()
                                .map(commentDTOMapper::convertToDTO)
                                .collect(Collectors.toList());

                return commentDTOs;
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
        @ResponseStatus(HttpStatus.CREATED)
        public Comment createComment(
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
                newComment.setCreationDate(LocalDateTime.now());

                return this.commentRepository.save(newComment);
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

        @PutMapping("{authorId}/articles/{articleId}/add-comment")
        public ResponseEntity<Article> addCommentToArticle(
                        @PathVariable UUID articleId,
                        @RequestBody Comment newComment,
                        @PathVariable UUID authorId) {

                Article article = articleRepository.findById(articleId).orElseThrow(
                                () -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Article Not Found: " + articleId));

                User author = userRepository.findById(authorId).orElseThrow(
                                () -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Author Not Found: " + authorId));

                newComment.setId(null);
                newComment.setArticle(article);
                newComment.setAuthor(author);
                newComment.setArchive(false);
                newComment.setCreationDate(LocalDateTime.now());
                newComment.setContent(newComment.getContent());

                article.getComments().add(newComment);

                Article updatedArticle = articleRepository.save(article);

                return ResponseEntity.ok(updatedArticle);
        }

        // @PutMapping("{authorId}/articles/{articleId}/add-comment")
        // public ResponseEntity<CommentDTO> addCommentToArticle(
        // @PathVariable UUID articleId,
        // @RequestBody CommentDTO newCommentDTO,
        // @PathVariable UUID authorId) {

        // Article article = articleRepository.findById(articleId).orElseThrow(
        // () -> new ResponseStatusException(
        // HttpStatus.NOT_FOUND, "Article Not Found: " + articleId));

        // User author = userRepository.findById(authorId).orElseThrow(
        // () -> new ResponseStatusException(
        // HttpStatus.NOT_FOUND, "Author Not Found: " + authorId));

        // Comment newComment = new Comment();
        // BeanUtils.copyNonNullProperties(newCommentDTO, newComment);
        // newComment.setId(null);
        // newComment.setArticle(article);
        // newComment.setAuthor(author);
        // newComment.setArchive(false);
        // newComment.setCreationDate(LocalDateTime.now());

        // article.getComments().add(newComment);

        // article = articleRepository.save(article);

        // CommentDTO addedCommentDTO = commentDTOMapper.convertToDTO(newComment);

        // return ResponseEntity.ok(addedCommentDTO);
        // }
        // @PutMapping("{authorId}/articles/{articleId}/add-comment")
        // public ResponseEntity<Article> addCommentToArticle(
        // @PathVariable UUID articleId,
        // @RequestBody Comment newComment,
        // @PathVariable UUID authorId) {

        // Article article = articleRepository.findById(articleId).orElseThrow(
        // () -> new ResponseStatusException(
        // HttpStatus.NOT_FOUND, "Article Not Found: " + articleId));

        // User author = userRepository.findById(authorId).orElseThrow(
        // () -> new ResponseStatusException(
        // HttpStatus.NOT_FOUND, "Author Not Found: " + authorId));

        // newComment.setId(null);
        // newComment.setArticle(article);
        // newComment.setAuthor(author);
        // newComment.setArchive(false);
        // newComment.setCreationDate(LocalDateTime.now());
        // newComment.setContent(newComment.getContent());

        // article.getComments().add(newComment);

        // Article updatedArticle = articleRepository.save(article);

        // return ResponseEntity.ok(updatedArticle);
        // }

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
