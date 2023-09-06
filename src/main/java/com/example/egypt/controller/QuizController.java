package com.example.egypt.controller;

import com.example.egypt.entity.*;
import com.example.egypt.repository.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
    private QuizRepository quizRepository;
    private UserRepository userRepository;
    private BadgeRepository badgeRepository;
    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;

    QuizController(QuizRepository quizRepository, UserRepository userRepository, BadgeRepository badgeRepository, CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.commentRepository=commentRepository;
        this.articleRepository =articleRepository;
    }
    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return this.quizRepository.findAll();
    }

    @GetMapping("/{id}/{userId}")
    public Quiz getById(@PathVariable UUID id, @PathVariable UUID userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Author not found: " + userId));
        return this.quizRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{userId}")
    public Quiz getById(@PathVariable UUID userId) {
        return this.quizRepository
                .findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/create/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Quiz createQuiz(
            @PathVariable UUID userId,
            @RequestBody Quiz newQuiz) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Not Found" + userId));

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        newQuiz.setCreationDate(localDateTimeNow);
        newQuiz.setAuthor(user);
        newQuiz.setContent(newQuiz.getContent());
        return this.quizRepository.save(newQuiz);
    }
    @PutMapping("/{id}/badges/{badgeId}/add-badges")
    public ResponseEntity<Quiz> addBadge(
            @PathVariable UUID id,
            @PathVariable UUID badgeId,
            @RequestBody @Validated Quiz addBadgeQuiz) {

     Badge badge = badgeRepository.findById(badgeId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + badgeId));

        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article Not Found: " + id));


        quiz.getBadge().add(badge);


    Quiz updatedQuiz = quizRepository.save(quiz);

        return ResponseEntity.ok(updatedQuiz);
    }
    @PutMapping("/{id}/comments/{commentId}/add-comments")
    public ResponseEntity<Quiz> addCommentToQuiz(
            @PathVariable UUID id,
            @PathVariable UUID commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment Not Found: " + commentId));

        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));


        quiz.getComments().add(comment);

        Quiz updatedQuiz = quizRepository.save(quiz);

        return ResponseEntity.ok(updatedQuiz);
    }
    @PutMapping("/{id}/articles/{articleId}/add-articles")
    public ResponseEntity<Quiz> addArticleToQuiz(
            @PathVariable UUID id,
            @PathVariable UUID articleId) {

      Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article Not Found: " + articleId));

        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));


        quiz.getArticles().add(article);

        Quiz updatedQuiz = quizRepository.save(quiz);

        return ResponseEntity.ok(updatedQuiz);
    }
    @PutMapping("/{id}/rating")
    public ResponseEntity<Quiz> updateRating(
            @PathVariable UUID id,
            @RequestBody Float newRating) {

        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));


        quiz.setRating(newRating);

        Quiz updatedQuiz = quizRepository.save(quiz);

        return ResponseEntity.ok(updatedQuiz);
    }




    @PutMapping("/{id}/add-rating")
    public ResponseEntity<Quiz> addRating(
            @PathVariable UUID id,
            @RequestBody List<Float> newRatings) {

        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));

        Float currentRating = quiz.getRating();
        if (currentRating == null) {
            currentRating = 0.0f;
        }

        // Additionnez les nouvelles notes à la note actuelle
        Float sumOfRatings = currentRating;
        for (Float newRating : newRatings) {
            sumOfRatings += newRating;
        }

        // Calcul de la moyenne
        Float averageRating = sumOfRatings / (newRatings.size() + 1); // +1 pour inclure la note actuelle

        // Arrondissez la moyenne à un chiffre après la virgule
        float roundedAverage = Math.round(averageRating * 10.0f) / 10.0f;

        quiz.setRating(roundedAverage);

        Quiz updatedQuiz = quizRepository.save(quiz);

        return ResponseEntity.ok(updatedQuiz);
    }

    @PutMapping("/{id}")
    public Quiz update(@PathVariable UUID id, @RequestBody Quiz quizUpdated) {
        quizUpdated.setId(id);
        return this.quizRepository.save(quizUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.quizRepository.deleteById(id);
    }
}
