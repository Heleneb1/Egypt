package com.example.egypt.controller;

import com.example.egypt.DTO.QuestionDTO;
import com.example.egypt.DTOMapper.QuestionDTOMapper;
import com.example.egypt.entity.*;
import com.example.egypt.repository.*;
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

    private static QuestionDTOMapper questionDTOMapper;

    QuestionController(QuestionRepository questionRepository,
                       UserRepository userRepository,
                       BadgeRepository badgeRepository,
                       CommentRepository commentRepository,
                       ArticleRepository articleRepository,
                       QuestionDTOMapper questionDTOMapper) {
        this.questionRepository = questionRepository;

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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Question createQuestion(

            @RequestBody Question newQuestion) {

        return this.questionRepository.save(newQuestion);
    }

    @GetMapping("/category/{category}")
    public List<QuestionDTO> getByCategory(@PathVariable String category) {
        QuestionService questionService = new QuestionService(questionRepository, questionDTOMapper,
                questionRepository);
        List<QuestionDTO> questionDTOS = questionService.searchQuestionByCategory(category);
        return questionDTOS;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Question update(@PathVariable UUID id, @RequestBody Question questionUpdated) {
        questionUpdated.setId(id);
        return this.questionRepository.save(questionUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.questionRepository.deleteById(id);
    }
}
