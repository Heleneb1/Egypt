package com.example.egypt.controller;

import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.DTOMapper.ArticleDTOMapper;
import com.example.egypt.entity.Article;
import com.example.egypt.entity.Comment;
import com.example.egypt.entity.Quiz;
import com.example.egypt.entity.User;
import com.example.egypt.repository.ArticleRepository;
import com.example.egypt.repository.QuizRepository;
import com.example.egypt.repository.UserRepository;
import com.example.egypt.services.ArticleService;
import com.example.egypt.services.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private UserRepository userRepository;
    private final ArticleDTOMapper articleDTOMapper;

    ArticleController(ArticleRepository articleRepository,
                      QuizRepository quizRepository,
                      UserRepository userRepository,
                      ArticleDTOMapper articleDTOMapper) {
        this.articleRepository = articleRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
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
    @GetMapping("/byTag/{tag}")
    public List<ArticleDTO> getByTag(@PathVariable String tag) {
        ArticleService articleService = new ArticleService(
                articleRepository, articleDTOMapper, quizRepository);
        List<ArticleDTO> articles = articleService.findByTag(tag);
        return articles;
    }
    @GetMapping("/byTitle/{title}")
    public List<ArticleDTO> getByTitle(@PathVariable String title) {
        ArticleService articleService = new ArticleService(
                articleRepository, articleDTOMapper, quizRepository);
        List<ArticleDTO> articles = articleService.findByTitle(title);
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
    @ResponseStatus(HttpStatus.CREATED)
    public Article create(@PathVariable UUID userId, @RequestBody Article newArticle) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Not Found" + userId));

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        newArticle.setEditionDate(localDateTimeNow);
        return this.articleRepository.save(newArticle);
    }
//@PostMapping("/create")
//@PreAuthorize("hasRole('ADMIN')")
//public Article create(@RequestBody Article newArticle) {
//    LocalDateTime localDateTimeNow = LocalDateTime.now();
//    newArticle.setEditionDate(localDateTimeNow);
//    return this.articleRepository.save(newArticle);
//}
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


        newComment.setArticle(article);


        article.getComments().add(newComment);

        Article updatedArticle = articleRepository.save(article);

        return ResponseEntity.ok(updatedArticle);
    }
    @PutMapping("/{id}/add-rating")
    public ResponseEntity<ArticleDTO> addRating(
            @PathVariable UUID id,
            @RequestBody  List<Float> newRatings,
            @Validated ArticleDTO addRating) {

      Article article = articleRepository.findById(id)
              .orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article Not Found: " + id));

        Float currentRating = article.getRating();
        if (currentRating == null) {
            currentRating = 0.0f;
        }

        Float sumOfRatings = currentRating;
        for (Float newRating : newRatings) {
            sumOfRatings += newRating;
        }

        Float averageRating = sumOfRatings / (newRatings.size() + 1); // +1 pour inclure la note actuelle

        float roundedAverage = Math.round(averageRating * 10.0f) / 10.0f;

        article.setRating(roundedAverage);
        BeanUtils.copyNonNullProperties(addRating, article);

       Article updatedArticle = articleRepository.save(article);
       ArticleDTO updatedArticleDTO = articleDTOMapper.convertToDTO(updatedArticle);

        return ResponseEntity.ok(updatedArticleDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.articleRepository.deleteById(id);
    }

}

