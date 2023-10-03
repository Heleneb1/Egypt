package com.example.egypt.DTOMapper;

import com.example.egypt.DTO.QuizDTO;

import com.example.egypt.entity.Quiz;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class QuizDTOMapper implements Function<Quiz, QuizDTO> {

    @Override
    public QuizDTO apply(Quiz quiz) {
        return new QuizDTO(
                quiz.getId(),
                quiz.getContent(),
                quiz.getTitle(),
                quiz.getDifficulty(),
                quiz.getCreationDate(),
                quiz.getArticle(),
                quiz.getPicture(),
                quiz.getRating(),
                quiz.getArchive(),
                quiz.getAuthor() == null ? null : quiz.getAuthor().getId(), // en cas d'auteur null pour eviter
                                                                            // nullPointerException
                quiz.getBadge() == null ? null : quiz.getBadge().getId(),
                quiz.getArticles(),
                quiz.getComments(),
                quiz.getRatings(),
                quiz.getQuestions()

        );
    }

    public QuizDTO convertToDTO(Quiz quiz) {
        return apply(quiz);
    }
}
