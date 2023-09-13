package com.example.egypt.services;


import com.example.egypt.DTO.QuizDTO;

import com.example.egypt.DTOMapper.QuizDTOMapper;

import com.example.egypt.entity.Quiz;

import com.example.egypt.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuizService {


    private static QuizRepository quizRepository;
    private static QuizDTOMapper quizDTOMapper;


    public QuizService(QuizRepository quizRepository,
                       QuizDTOMapper quizDTOMapper
    ) {

        this.quizRepository = quizRepository;
        this.quizDTOMapper = quizDTOMapper;
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


}


