package com.example.egypt.DTOMapper;

import com.example.egypt.entity.Topic;
import com.example.egypt.DTO.TopicDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class TopicDTOMapper implements Function <Topic, TopicDTO>{
    @Override
    public TopicDTO apply(Topic topic){
        return new TopicDTO(
                topic.getId(),
                topic.getCreationDate(),
                topic.getAuthor().getId(),
//                topic.getReceiver().getId(),
                topic.getTag(),
                topic.getMessage(),
                topic.getAnswers()
        );
    }

    public TopicDTO convertToDTO(Topic topic) {
        return apply(topic);
    }
}