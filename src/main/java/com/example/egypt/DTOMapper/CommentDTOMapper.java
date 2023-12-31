package com.example.egypt.DTOMapper;

import com.example.egypt.DTO.CommentDTO;
import com.example.egypt.entity.Comment;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDTOMapper implements Function<Comment, CommentDTO> {

    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreationDate(),
                comment.getArchive(),
                comment.getAuthor() == null ? null : comment.getAuthor().getId(),
                comment.getQuiz(),
                comment.getArticle());
    }

    public CommentDTO convertToDTO(Comment comment) {
        return apply(comment);
    }

}
