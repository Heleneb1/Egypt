package com.example.egypt.services;


import com.example.egypt.DTO.QuizDTO;

import com.example.egypt.DTOMapper.QuizDTOMapper;

import com.example.egypt.entity.Question;
import com.example.egypt.entity.Quiz;

import com.example.egypt.repository.QuestionRepository;
import com.example.egypt.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuizService {


    private static QuizRepository quizRepository;
    private static QuizDTOMapper quizDTOMapper;
    private QuestionRepository questionRepository;


    public QuizService(QuizRepository quizRepository,
                       QuizDTOMapper quizDTOMapper,
                       QuestionRepository questionRepository
    ) {

        this.quizRepository = quizRepository;
        this.quizDTOMapper = quizDTOMapper;
        this.questionRepository = questionRepository;
    }

    public List<QuizDTO> findAll() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(quizDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public static Optional<QuizDTO> findById(UUID id) {
        return quizRepository.findById(id)
                .map(quizDTOMapper::convertToDTO);
    }


    public List<QuizDTO> findByAuthor(UUID authorId) {
        List<Quiz> quizzes = quizRepository.findByAuthorId(authorId);
        return quizzes.stream()
                .map(quizDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }
    public Quiz createQuiz(String category) {
        // Récupérez les questions de la base de données en fonction de la catégorie
        List<Question> questions = questionRepository.findByCategoryContaining(category);

        // Créez un nouvel objet Quiz avec les questions récupérées
        Quiz newQuiz = new Quiz();
        newQuiz.setQuestions(questions); // Assurez-vous d'avoir un setter approprié dans la classe Quiz

        // Sauvegardez le quiz dans la base de données
        return quizRepository.save(newQuiz);
    }

}


