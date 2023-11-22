package com.example.egypt.services;

import com.example.egypt.DTO.AnswerDTO;
import com.example.egypt.DTOMapper.AnswerDTOMapper;
import com.example.egypt.entity.Answer;
import com.example.egypt.entity.Topic;
import com.example.egypt.entity.User;
import com.example.egypt.repository.AnswerRepository;
import com.example.egypt.repository.TopicRepository;
import com.example.egypt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnswerService {
    private static AnswerRepository answerRepository;
    private static UserRepository userRepository;
    private static TopicRepository topicRepository;
    private static AnswerDTOMapper answerDTOMapper;
    public AnswerService (AnswerRepository answerRepository,
                          AnswerDTOMapper answerDTOMapper, UserRepository userRepository, TopicRepository topicRepository){
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.answerDTOMapper = answerDTOMapper;
    }
public static Optional<AnswerDTO> findById(UUID id){
        return answerRepository.findById(id)
                .map(answerDTOMapper::convertToDTO);
}
public List<AnswerDTO> findAll(){
        List<Answer> answers = answerRepository.findAll();
                return answers.stream()
                        .map(answerDTOMapper::convertToDTO)
                        .collect(Collectors.toList());
}
    public List<AnswerDTO> findByTopic(Topic topic) {
        List<Answer> answers = answerRepository.findByTopic(topic);
        return answers.stream()
                .map(answerDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }



}
