package com.example.egypt.DTO;

import com.example.egypt.entity.Topic;
import com.example.egypt.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public record AnswerDTO(UUID id,
                        @JsonIgnore User author,
                        @JsonIgnore Topic topic,
                        String answer) {

    public UUID getAuthorId() {
        return author.getId();
    }

    public UUID getTopicId() {
        return topic.getId();
    }
}
