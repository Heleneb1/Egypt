package com.example.egypt.services;

import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.DTO.TopicDTO;

import com.example.egypt.DTOMapper.TopicDTOMapper;
import com.example.egypt.entity.Article;
import com.example.egypt.entity.Topic;
import com.example.egypt.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private static TopicRepository topicRepository;
    private static TopicDTOMapper topicDTOMapper;

    public TopicService(TopicRepository topicRepository, TopicDTOMapper topicDTOMapper) {
        this.topicRepository = topicRepository;
        this.topicDTOMapper = topicDTOMapper;
    }

    public Optional<TopicDTO> findById(UUID id) {
        return topicRepository.findById(id)
                .map(topicDTOMapper::convertToDTO);
    }
    public List<TopicDTO> findAll() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(topicDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TopicDTO> findByTag(String tag) {
        List<Topic> topics = topicRepository.findAllByTagContainingIgnoreCase(tag);
        return topics.stream()
                .map(topicDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }

}
