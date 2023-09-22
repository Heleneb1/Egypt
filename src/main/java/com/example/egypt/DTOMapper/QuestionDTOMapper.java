package com.example.egypt.DTOMapper;

import com.example.egypt.DTO.QuestionDTO;
import com.example.egypt.DTO.QuizDTO;
import com.example.egypt.entity.Question;
import com.example.egypt.entity.Quiz;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class QuestionDTOMapper implements Function<Question, QuestionDTO> {

    @Override
    public QuestionDTO apply(Question question) {
        return new QuestionDTO(
               question.getId(),
                question.getCategory(),
                question.getOption_1(),
                question.getOption_2(),
                question.getOption_3(),
                question.getQuestion_title(),
                question.getRight_answer(),
                question.getRight_answer_2(),
                question.getQuiz()
        );
    }


    public QuestionDTO convertToDTO(Question question) {
        return apply(question);
    }
}
