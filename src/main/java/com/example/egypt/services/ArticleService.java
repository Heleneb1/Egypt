package com.example.egypt.services;

import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.DTOMapper.ArticleDTOMapper;
import com.example.egypt.entity.Article;
import com.example.egypt.repository.ArticleRepository;
import com.example.egypt.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class ArticleService {

        private static ArticleRepository articleRepository;
        private static ArticleDTOMapper articleDTOMapper;
        private final QuizRepository quizRepository;


        public ArticleService(ArticleRepository articleRepository,
                              ArticleDTOMapper articleDTOMapper,
                              QuizRepository quizRepository) {
            this.articleRepository = articleRepository;
            this.articleDTOMapper = articleDTOMapper;
            this.quizRepository = quizRepository;
        }

        public static Optional<ArticleDTO> findById(UUID id) {
            return articleRepository.findById(id)
                    .map(articleDTOMapper::convertToDTO);
        }


        public List<ArticleDTO> findByQuiz(UUID quizId) {
            List<Article> articles = articleRepository.findByQuizzesId(quizId);
            return articles.stream()
                    .map(articleDTOMapper::convertToDTO)
                    .collect(Collectors.toList());
        }

        public List<ArticleDTO> findByAuthor(String author) {
            List<Article> articles = articleRepository.findArticlesByAuthorContaining(author);
            return articles.stream()
                    .map(articleDTOMapper::convertToDTO)
                    .collect(Collectors.toList());
        }

    public List<ArticleDTO> findByTag(String tag) {
        List<Article> articles = articleRepository.findArticlesByTagContainingIgnoreCase(tag);
        return articles.stream()
                .map(articleDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<ArticleDTO> findByTitle(String title) {
        List<Article> articles = articleRepository.findByTitleContainingIgnoreCase(title);
        return articles.stream()
                .map(articleDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> findAllArticles() {
            List<Article> articles=articleRepository.findAll();
            return articles.stream()
                    .map(articleDTOMapper::convertToDTO)
                    .collect(Collectors.toList());
    }

}


