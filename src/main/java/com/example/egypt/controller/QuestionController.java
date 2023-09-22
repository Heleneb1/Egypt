package com.example.egypt.controller;

import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.DTO.QuestionDTO;
import com.example.egypt.DTOMapper.QuestionDTOMapper;
import com.example.egypt.entity.*;
import com.example.egypt.repository.*;
import com.example.egypt.services.ArticleService;
import com.example.egypt.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private BadgeRepository badgeRepository;
    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    private static QuestionDTOMapper questionDTOMapper;

    QuestionController(QuestionRepository questionRepository,
                       UserRepository userRepository,
                       BadgeRepository badgeRepository,
                       CommentRepository commentRepository,
                       ArticleRepository articleRepository,
                       QuestionDTOMapper questionDTOMapper) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.questionDTOMapper = questionDTOMapper;
    }

    @GetMapping("")
    public List<QuestionDTO> getAllQuestions() {
        QuestionService questionService = new QuestionService(
                questionRepository, questionDTOMapper,
                questionRepository);
        List<QuestionDTO> questionDTOS = questionService.findAll();
        return questionDTOS;
    }

    @GetMapping("/{id}")
    public QuestionDTO getById(@PathVariable UUID id) {

        Question question = this.questionRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        QuestionDTO questionDTOs = questionDTOMapper.apply(question);

        return questionDTOs;
    }

//    @GetMapping("/{userId}")
//    public QuizDTO getById(@PathVariable UUID userId) {
//        return this.quizRepository
//                .findById(userId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        return quizDTOs;
//    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Question createQuestion(

            @RequestBody Question newQuestion) {

        return this.questionRepository.save(newQuestion);
    }

//    @PutMapping("/{id}/badges/{badgeId}/add-badges")
//    public ResponseEntity<Quiz> addBadge(
//            @PathVariable UUID id,
//            @PathVariable UUID badgeId,
//            @RequestBody @Validated Quiz addBadgeQuiz) {
//
//        Badge badge = badgeRepository.findById(badgeId).orElseThrow(
//                () -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + badgeId));
//
//        Quiz quiz = quizRepository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Article Not Found: " + id));
//
//
//        quiz.getBadge().add(badge);
//
//
//        Quiz updatedQuiz = quizRepository.save(quiz);
//
//        return ResponseEntity.ok(updatedQuiz);
//    }
//
//    @PutMapping("/{id}/comments/{commentId}/add-comments")
//    public ResponseEntity<Quiz> addCommentToQuiz(
//            @PathVariable UUID id,
//            @PathVariable UUID commentId) {
//
//        Comment comment = commentRepository.findById(commentId).orElseThrow(
//                () -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Comment Not Found: " + commentId));
//
//        Quiz quiz = quizRepository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));
//
//
//        quiz.getComments().add(comment);
//
//        Quiz updatedQuiz = quizRepository.save(quiz);
//
//        return ResponseEntity.ok(updatedQuiz);
//    }
//
//    @PutMapping("/{id}/articles/{articleId}/add-articles")
//    public ResponseEntity<Quiz> addArticleToQuiz(
//            @PathVariable UUID id,
//            @PathVariable UUID articleId) {
//
//        Article article = articleRepository.findById(articleId).orElseThrow(
//                () -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Article Not Found: " + articleId));
//
//        Quiz quiz = quizRepository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));
//
//
//        quiz.getArticles().add(article);
//
//        Quiz updatedQuiz = quizRepository.save(quiz);
//
//        return ResponseEntity.ok(updatedQuiz);
//    }
//
//    @PutMapping("/{id}/rating")
//    public ResponseEntity<Quiz> updateRating(
//            @PathVariable UUID id,
//            @RequestBody Float newRating) {
//
//        Quiz quiz = quizRepository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));
//
//
//        quiz.setRating(newRating);
//
//        Quiz updatedQuiz = quizRepository.save(quiz);
//
//        return ResponseEntity.ok(updatedQuiz);
//    }
//
//
//    @PutMapping("/{id}/add-rating")
//    public ResponseEntity<Quiz> addRating(
//            @PathVariable UUID id,
//            @RequestBody List<Float> newRatings) {
//
//        Quiz quiz = quizRepository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Quiz Not Found: " + id));
//
//        Float currentRating = quiz.getRating();
//        if (currentRating == null) {
//            currentRating = 0.0f;
//        }
//
//        // Additionnez les nouvelles notes à la note actuelle
//        Float sumOfRatings = currentRating;
//        for (Float newRating : newRatings) {
//            sumOfRatings += newRating;
//        }
//
//        // Calcul de la moyenne
//        Float averageRating = sumOfRatings / (newRatings.size() + 1); // +1 pour inclure la note actuelle
//
//        // Arrondissez la moyenne à un chiffre après la virgule
//        float roundedAverage = Math.round(averageRating * 10.0f) / 10.0f;
//
//        quiz.setRating(roundedAverage);
//
//        Quiz updatedQuiz = quizRepository.save(quiz);
//
//        return ResponseEntity.ok(updatedQuiz);
//    }
//
//    //Todo adapter le code au code du dessous
//    @PutMapping("/{id}")
//    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable UUID id, @RequestBody @Validated Quiz quizDTO) {
//        Quiz quizUpdated = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
//                HttpStatus.NOT_FOUND, "Quiz not found : " + id));
//        BeanUtils.copyNonNullProperties(quizDTO, quizUpdated);
//        Quiz savedQuiz = quizRepository.save(quizUpdated);
//        QuizDTO updatedAnswerDTO = quizDTOMapper.convertToDTO(savedQuiz);
//        return ResponseEntity.ok(updatedAnswerDTO);
//    }
@PostMapping("/category/{category}")
public  List<QuestionDTO>getByCategory(@PathVariable String category){
        QuestionService questionService= new QuestionService(questionRepository,questionDTOMapper,questionRepository);
        List<QuestionDTO> questionDTOS=questionService.searchQuestionByCategory(category);
        return questionDTOS;
}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.questionRepository.deleteById(id);
    }
}
