package com.example.egypt.DTOMapper;

import com.example.egypt.DTO.AnswerDTO;
import com.example.egypt.entity.Answer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AnswerDTOMapper implements Function<Answer, AnswerDTO> {

    @Override
    public AnswerDTO apply(Answer answer) {
        return new AnswerDTO(
                answer.getId(),
                answer.getAuthor(),
                answer.getTopic(),
                answer.getAnswer()
        );
    }

    public AnswerDTO convertToDTO(Answer answer) {
        return apply(answer);
    }
}
