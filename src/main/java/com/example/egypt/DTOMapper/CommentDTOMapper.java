package com.example.egypt.DTOMapper;

import com.example.egypt.DTO.CommentDTO;
import com.example.egypt.entity.Comment;

import java.time.LocalDateTime;
import java.util.function.Function;

public class CommentDTOMapper implements Function<Comment, CommentDTO> {


    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreationDate(),
                comment.getArchive(),
                comment.getAuthor().getId(),
                comment.getQuiz(),
                comment.getArticle()
        );
    }

    public LocalDateTime setCreationDate(LocalDateTime localDateTimeNow) {
        return localDateTimeNow;
    }

    public CommentDTO convertToDTO(Comment comment) {
        return apply(comment);
    }
}
