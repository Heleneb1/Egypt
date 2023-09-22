package com.example.egypt.DTO;

import com.example.egypt.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record QuestionDTO(
        UUID id,
        String category,
        String option_1,
        String option_2,
        String option_3,
        String question_title,
        String right_answer,
        String right_answer_2,
        Quiz quiz
) {

}
