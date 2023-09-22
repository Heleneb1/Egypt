package com.example.egypt.controller;

import com.example.egypt.DTO.QuizDTO;
import com.example.egypt.DTOMapper.QuizDTOMapper;
import com.example.egypt.entity.*;
import com.example.egypt.repository.*;

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
        this.quizService =quizService;
        this.questionRepository=questionRepository;
    }

    @GetMapping("")
    public List<QuizDTO> getAllQuizzes() {
        QuizService quizService = new QuizService(
                quizRepository, quizDTOMapper, questionRepository
        );
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

//    @GetMapping("/{userId}")
//    public QuizDTO getById(@PathVariable UUID userId) {
//        return this.quizRepository
//                .findById(userId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        return quizDTOs;
//    }

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
        newQuiz.setArchive(false);
        newQuiz.setContent(newQuiz.getContent());
        return this.quizRepository.save(newQuiz);
    }
//    @PostMapping("/create/add-question")
//    public Quiz createQuizAddQuestion(@RequestParam String category, @RequestBody Quiz newQuiz){
//        newQuiz = quizService.createQuizAndQuestion(category);
//        LocalDateTime localDateTimeNow = LocalDateTime.now();
//        newQuiz.setCreationDate(localDateTimeNow);
//        newQuiz.setArchive(false);
//        return this.quizRepository.save(newQuiz);
//    }

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


    @PostMapping("/{id}/add-questions")
    public ResponseEntity<Quiz> addQuestionsByCategory(
            @PathVariable UUID id,
            @RequestParam String category) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));

        // Récupérez les questions de la base de données en fonction de la catégorie
        List<Question> questionsToAdd = questionRepository.findByCategoryContaining(category);

        // Ajoutez les questions récupérées au quiz existant
        List<Question> currentQuestions = quiz.getQuestions();
        if (currentQuestions == null) {
            currentQuestions = new ArrayList<>();
        }
        currentQuestions.addAll(questionsToAdd);

        // Mettez à jour la liste de questions du quiz
        quiz.setQuestions(currentQuestions);

        // Sauvegardez le quiz mis à jour dans la base de données
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
//Todo adapter le code au code du dessous
    @PutMapping("/{id}")
    public ResponseEntity <QuizDTO> updateQuiz(@PathVariable UUID id, @RequestBody  @Validated Quiz quizDTO) {
        Quiz quizUpdated = quizRepository.findById(id)  .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Quiz not found : " +id));
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
