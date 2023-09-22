package com.example.egypt.services;


import com.example.egypt.DTO.QuestionDTO;
import com.example.egypt.DTOMapper.QuestionDTOMapper;
import com.example.egypt.entity.Question;
import com.example.egypt.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {


    private static QuestionRepository questionRepository;
    private static QuestionDTOMapper questionDTOMapper;


    public QuestionService(QuestionRepository questionRepository,
                           QuestionDTOMapper questionDTOMapper,
                           QuestionRepository repository) {

        this.questionRepository = questionRepository;
        this.questionDTOMapper= questionDTOMapper;
    }

    public List<QuestionDTO> findAll() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(questionDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public static Optional<QuestionDTO> findById(UUID id) {
        return questionRepository.findById(id)
                .map(questionDTOMapper::convertToDTO);
    }

    public List<QuestionDTO> searchQuestionByCategory(String category) {
        List<Question> questions =questionRepository.findByCategoryContaining(category);
        return questions.stream()
                .map(questionDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }
//    public List<QuestionDTO> findByAuthorId(UUID authorId) {
//        List<Question> questions = questionRepository.findByAuthorId(authorId);
//        return questions.stream()
//                .map(questionDTOMapper::convertToDTO)
//                .collect(Collectors.toList());
//    }


}


