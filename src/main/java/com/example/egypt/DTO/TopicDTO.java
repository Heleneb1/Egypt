package com.example.egypt.DTO;

import com.example.egypt.entity.Answer;
import com.example.egypt.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record TopicDTO( UUID id,
                        LocalDateTime creationDate,
                        UUID authorId,
//                        UUID receiverId,
                        String tag,
                        String message,
                        @JsonIgnore List<Answer> answers) {

    public List<UUID> getAnswersId(){
        return answers.stream()
                .map(Answer::getId)
                .collect(Collectors.toList());
    }
}

