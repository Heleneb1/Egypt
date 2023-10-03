package com.example.egypt.DTO;

import com.example.egypt.entity.*;
import java.util.UUID;

public record QuestionDTO(
                UUID id,
                String category,
                String option_1,
                String option_2,
                String option_3,
                String question_title,
                String right_answer,
                String right_answer_2,
                Quiz quiz) {

}
