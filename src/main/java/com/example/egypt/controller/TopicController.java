package com.example.egypt.controller;

import com.example.egypt.DTO.TopicDTO;
import com.example.egypt.DTOMapper.TopicDTOMapper;
import com.example.egypt.entity.*;
import com.example.egypt.repository.AnswerRepository;
import com.example.egypt.repository.TopicRepository;
import com.example.egypt.repository.UserRepository;
import com.example.egypt.services.BeanUtils;
import com.example.egypt.services.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    private final TopicDTOMapper topicDTOMapper;

    TopicController(TopicRepository topicRepository, UserRepository userRepository, AnswerRepository answerRepository,
            TopicDTOMapper topicDTOMapper) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.topicDTOMapper = topicDTOMapper;
    }

    // ok
    @GetMapping("")
    public List<TopicDTO> getAll() {
        TopicService topicService = new TopicService(
                topicRepository, topicDTOMapper);
        List<TopicDTO> topicDTOS = topicService.findAll();
        return topicDTOS;
    }

    @GetMapping("/{id}")
    public TopicDTO getById(@PathVariable UUID id) {
        Topic topic = this.topicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return topicDTOMapper.convertToDTO(topic);
    }

    @GetMapping("/byTag/{tag}")
    public List<TopicDTO> getByTag(@PathVariable String tag) {
        TopicService topicService = new TopicService(
                topicRepository, topicDTOMapper);
        List<TopicDTO> topics = topicService.findByTag(tag);
        return topics;
    }

    // ok
    @PostMapping("/{authorId}/create")
    public ResponseEntity<Topic> create(@PathVariable UUID authorId, @RequestBody @Validated Topic newTopic) {
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        newTopic.setCreationDate(localDateTimeNow);

        User author = this.userRepository
                .findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        newTopic.setAuthor(author);

        Topic createTopic = topicRepository.save(newTopic);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTopic);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Topic updateTopic(@PathVariable UUID id, @RequestBody Topic topicUpdated) {
        Topic existingTopic = this.topicRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found for update id:" + id));
        BeanUtils.copyNonNullProperties(topicUpdated, existingTopic);
        return this.topicRepository.save(existingTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!this.topicRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.topicRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}