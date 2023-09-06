package com.example.egypt.services;

import java.util.List;
import java.util.stream.Collectors;

import com.example.egypt.DTO.CommentDTO;

import com.example.egypt.DTOMapper.CommentDTOMapper;
import com.example.egypt.entity.Comment;
import org.springframework.stereotype.Service;

import com.example.egypt.repository.CommentRepository;

@Service
public class CommentService {
    private static CommentRepository commentRepository;
    private static CommentDTOMapper commentDTOMapper;

    public CommentService(CommentRepository commentRepository,
                          CommentDTOMapper commentDTOMapper) {
        this.commentRepository = commentRepository;
        this.commentDTOMapper = commentDTOMapper;
    }

    public List<CommentDTO> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(commentDTOMapper::convertToDTO)
                .collect(Collectors.toList());

    }
}
