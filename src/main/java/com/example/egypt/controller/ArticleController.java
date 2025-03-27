package com.example.egypt.controller;

import com.example.egypt.DTO.AddCommentsDTO;
import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.DTOMapper.ArticleDTOMapper;
import com.example.egypt.entity.Article;
import com.example.egypt.entity.Comment;
import com.example.egypt.entity.Quiz;
import com.example.egypt.entity.Rating;
import com.example.egypt.entity.User;
import com.example.egypt.repository.ArticleRepository;
import com.example.egypt.repository.CommentRepository;
import com.example.egypt.repository.QuizRepository;
import com.example.egypt.repository.RatingRepository;
import com.example.egypt.repository.UserRepository;
import com.example.egypt.services.ArticleService;
import com.example.egypt.services.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/articles")

public class ArticleController {

    private ArticleRepository articleRepository;
    private QuizRepository quizRepository;
    private UserRepository userRepository;
    private ArticleDTOMapper articleDTOMapper;

    ArticleController(ArticleRepository articleRepository,
            QuizRepository quizRepository,
            UserRepository userRepository,
            ArticleDTOMapper articleDTOMapper,
            CommentRepository commentRepository) {
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

    @GetMapping("/byAuthor/{author}")
    public List<ArticleDTO> getArticlesByAuthor(@PathVariable String author) {
        ArticleService articleService = new ArticleService(
                articleRepository, articleDTOMapper, quizRepository);
        List<ArticleDTO> articles = articleService.findByAuthor(author);
        return articles;
    }

    @GetMapping("/search/{query}")
    public List<ArticleDTO> searchArticle(@PathVariable String query) {
        ArticleService articleService = new ArticleService(articleRepository, articleDTOMapper, quizRepository);
        List<ArticleDTO> articles = articleService.searchArticleByTitleOrAuthorOrTag(query, query, query);

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

    @GetMapping("slug/{slug}")
    public ResponseEntity<Article> getArticleBySlug(@PathVariable String slug) {
        System.out.println("Slug reçu: " + slug);
        Article article = articleRepository.findBySlug(slug);

        if (article != null) {
            System.out.println("Article trouvé: " + article.getTitle());
            return ResponseEntity.ok(article);
        }

        System.out.println("Article non trouvé pour le slug: " + slug);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")

    public ResponseEntity<Article> create(@RequestBody @Validated Article newArticle) {
        String slug = newArticle.getTitle().toLowerCase().replaceAll(" ", "-");
        newArticle.setSlug(slug); // Assigner le slug à l'article
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        newArticle.setEditionDate(localDateTimeNow);
        Article createArticle = articleRepository.save(newArticle);
        return ResponseEntity.status(HttpStatus.CREATED).body(createArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(
            @PathVariable UUID id, @RequestBody ArticleDTO articleDTO) {
        Article updatedArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article not found: " + id));
        BeanUtils.copyNonNullProperties(articleDTO, updatedArticle);
        Article savedArticle = articleRepository.save(updatedArticle);
        ArticleDTO updatedArticleDTO = articleDTOMapper.convertToDTO(savedArticle);
        return ResponseEntity.ok(updatedArticleDTO);
    }

    @PutMapping("/{id}/{userId}/add-comment")
    public ResponseEntity<ArticleDTO> addCommentToArticle(
            @PathVariable UUID id,
            @PathVariable UUID userId,
            @RequestBody AddCommentsDTO addCommentsDTO) {
        // Récupérez la liste des commentaires du DTO
        List<String> newComments = addCommentsDTO.comments();

        // Recherchez l'article en fonction de l'ID
        Article updatedArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article not found: " + id));

        // Recherchez l'utilisateur en fonction de l'ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found: " + userId));

        // Créez un nouveau commentaire pour chaque commentaire de la liste et
        // configurez-le
        for (String commentText : newComments) {
            Comment newComment = new Comment();
            newComment.setContent(commentText);
            newComment.setArticle(updatedArticle);
            newComment.setAuthor(user);
            // Ajoutez le commentaire à la liste des commentaires de l'article
            updatedArticle.getComments().add(newComment);
        }

        // Enregistrez l'article mis à jour dans la base de données
        Article savedArticle = articleRepository.save(updatedArticle);

        // Convertissez l'article mis à jour en DTO
        ArticleDTO updatedArticleDTO = articleDTOMapper.convertToDTO(savedArticle);

        return ResponseEntity.ok(updatedArticleDTO);
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

    @Autowired
    private RatingRepository ratingRepository;

    @PutMapping("/{id}/add-rating")
    public ResponseEntity<Article> addRating(
            @PathVariable UUID id,
            @RequestBody Map<String, Float> payload) {
        Float newRating = payload.get("rating");
        if (newRating == null || newRating < 0 || newRating > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid rating value");
        }

        Article article = articleRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Article Not Found: " + id));

        // Créer et sauvegarder la nouvelle note
        Rating rating = new Rating();
        rating.setArticle(article);
        rating.setRating(Math.round(newRating * 10.0f) / 10.0f);

        // Sauvegarder d'abord le rating
        ratingRepository.save(rating);

        // Ajouter à la liste des ratings et recalculer
        article.getRatings().add(rating);
        float averageRating = article.calculateAverageRating();
        article.setAverageRating(averageRating);

        Article updatedArticle = articleRepository.save(article);

        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.articleRepository.deleteById(id);
    }

}
