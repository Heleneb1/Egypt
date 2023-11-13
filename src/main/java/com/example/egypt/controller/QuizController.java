package com.example.egypt.controller;

import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.DTO.QuizDTO;
import com.example.egypt.DTOMapper.QuizDTOMapper;
import com.example.egypt.entity.*;
import com.example.egypt.repository.*;

import com.example.egypt.services.ArticleService;
import com.example.egypt.services.BeanUtils;
import com.example.egypt.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
    private QuizRepository quizRepository;
    private UserRepository userRepository;
    private BadgeRepository badgeRepository;
    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    private QuizService quizService;
    private static QuizDTOMapper quizDTOMapper;
    private QuestionRepository questionRepository;

    QuizController(QuizRepository quizRepository,
                   UserRepository userRepository,
                   BadgeRepository badgeRepository,
                   CommentRepository commentRepository,
                   ArticleRepository articleRepository,
                   QuizService quizService,
                   QuizDTOMapper quizDTOMapper, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.quizDTOMapper = quizDTOMapper;
        this.quizService = quizService;
        this.questionRepository = questionRepository;
    }

    @GetMapping("")
    public List<QuizDTO> getAllQuizzes() {
        QuizService quizService = new QuizService(
                quizRepository, quizDTOMapper, questionRepository);
        List<QuizDTO> quizDTOS = quizService.findAll();
        return quizDTOS;
    }

    @GetMapping("/{id}")
    public QuizDTO getById(@PathVariable UUID id) {

        Quiz quiz = this.quizRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        QuizDTO quizDTOs = quizDTOMapper.apply(quiz);

        return quizDTOs;
    }

    @GetMapping("/title/{title}")
    public List<QuizDTO> getQuizByTitle(@PathVariable String title) {
        QuizService quizService = new QuizService(
                quizRepository, quizDTOMapper, questionRepository);
        List<QuizDTO> quizzes = quizService.findByTitle(title);
        return quizzes;
    }


    @PostMapping("/create/{userId}")
    @ResponseStatus(HttpStatus.CREATED)

    public Quiz createQuiz(

            @RequestBody Quiz newQuiz) {

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        newQuiz.setCreationDate(localDateTimeNow);
        newQuiz.setArchive(newQuiz.getArchive());
        newQuiz.setContent(newQuiz.getContent());
        return this.quizRepository.save(newQuiz);
    }

    @PutMapping("/{id}/badges/{badgeId}/add-badge")
    public ResponseEntity<QuizDTO> addBadgeToQuiz(@PathVariable UUID id,
                                                  @PathVariable UUID badgeId) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz not found: " + id));

        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Badge not found: " + badgeId));

        quiz.setBadge(badge);

        quizRepository.save(quiz);

        QuizDTOMapper mapper = new QuizDTOMapper();
        QuizDTO quizDTO = mapper.convertToDTO(quiz);

        return ResponseEntity.ok(quizDTO);
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

    @PutMapping("/{id}/add-questions")
    public ResponseEntity<Quiz> addQuestionsByCategory(
            @PathVariable UUID id,
            @RequestBody Map<String, String> body) {
        String category = body.get("category");
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));

        // Récupération des questions de la base de données en fonction de la catégorie
        List<Question> questionsToAdd = questionRepository.findByCategoryContaining(category);

        // Ajout des questions récupérées au quiz existant
        List<Question> currentQuestions = (List<Question>) quiz.getQuestions();

        if (currentQuestions == null) {
            currentQuestions = new ArrayList<>();
        }

        for (Question question : questionsToAdd) {
            question.setQuiz(quiz); // Set the quiz for each question
            currentQuestions.add(question);
        }

        // Mise à jour liste de questions du quiz
        quiz.setQuestions(currentQuestions);

        // Sauvegarde quiz mis à jour dans la base de données
        Quiz updatedQuiz = quizRepository.save(quiz);

        return ResponseEntity.ok(updatedQuiz);
    }

    //        @PutMapping("/{id}/add-rating")
//        public ResponseEntity<Quiz> addRating(
//                        @PathVariable UUID id,
//                        @RequestBody List<Float> newRatings) {
//
//                Quiz quiz = quizRepository.findById(id).orElseThrow(
//                                () -> new ResponseStatusException(
//                                                HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));
//
//                Float currentRating = quiz.getRating();
//                if (currentRating == null) {
//                        currentRating = 0.0f;
//                }
//
//                // Additionnez les nouvelles notes à la note actuelle
//                Float sumOfRatings = currentRating;
//                for (Float newRating : newRatings) {
//                        sumOfRatings += newRating;
//                }
//
//                // Calcul de la moyenne
//                Float averageRating = sumOfRatings / (newRatings.size() + 1); // +1 pour inclure la note actuelle
//
//                // Arrondissez la moyenne à un chiffre après la virgule
//                float roundedAverage = Math.round(averageRating * 10.0f) / 10.0f;
//
//                quiz.setRating(roundedAverage);
//
//                Quiz updatedQuiz = quizRepository.save(quiz);
//
//                return ResponseEntity.ok(updatedQuiz);
//        }
    @PutMapping("/{id}/add-rating")
    public ResponseEntity<Quiz> addRating(
            @PathVariable UUID id,
            @RequestBody Map<String, Float> payload) {

        Float newRating = payload.get("rating");

        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));

        Float currentRating = quiz.getRating();
        if (currentRating == null) {
            currentRating = 0.0f;
        }

        // Additionnez les nouvelles notes à la note actuelle
        Float sumOfRatings = currentRating + newRating;

        // Calcul de la moyenne
        Float averageRating = sumOfRatings / 2; // Nous avons seulement deux notes : l'actuelle et la nouvelle

        // Arrondissez la moyenne à un chiffre après la virgule
        float roundedAverage = Math.round(averageRating * 10.0f) / 10.0f;

        quiz.setRating(roundedAverage);

        Quiz updatedQuiz = quizRepository.save(quiz);

        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/{id}/badges/remove")
    public ResponseEntity<QuizDTO> removeBadgeFromQuiz(@PathVariable UUID id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz not found: " + id));

        quiz.setBadge(null);

        quizRepository.save(quiz);

        QuizDTOMapper mapper = new QuizDTOMapper();
        QuizDTO quizDTO = mapper.convertToDTO(quiz);

        return ResponseEntity.ok(quizDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable UUID id, @RequestBody @Validated Quiz quizDTO) {
        Quiz quizUpdated = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Quiz not found : " + id));
        BeanUtils.copyNonNullProperties(quizDTO, quizUpdated);
        Quiz savedQuiz = quizRepository.save(quizUpdated);
        QuizDTO updatedAnswerDTO = quizDTOMapper.convertToDTO(savedQuiz);
        return ResponseEntity.ok(updatedAnswerDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.quizRepository.deleteById(id);
    }
}
