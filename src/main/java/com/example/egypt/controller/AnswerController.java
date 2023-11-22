package com.example.egypt.controller;

import com.example.egypt.DTO.AnswerDTO;
import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.DTOMapper.AnswerDTOMapper;
import com.example.egypt.entity.Answer;
import com.example.egypt.entity.Topic;
import com.example.egypt.entity.User;
import com.example.egypt.repository.AnswerRepository;
import com.example.egypt.repository.TopicRepository;
import com.example.egypt.repository.UserRepository;
import com.example.egypt.services.AnswerService;
import com.example.egypt.services.ArticleService;
import com.example.egypt.services.BeanUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private AnswerRepository answerRepository;
    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private AnswerDTOMapper answerDTOMapper;

    AnswerController(AnswerRepository answerRepository, AnswerDTOMapper answerDTOMapper, TopicRepository topicRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.answerDTOMapper = answerDTOMapper;

    }

    //Ok
    @GetMapping("")
    List<Answer> getAll() {
        return this.answerRepository.findAll();
    }

    @GetMapping("/{id}")
    public AnswerDTO getById(@PathVariable UUID id) {
        Answer answer = this.answerRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "answer not found"));
    return answerDTOMapper.convertToDTO(answer);
    }

    //Ok
    @GetMapping("/topics/{topicId}")
    public List<AnswerDTO> getAllByTopic(@PathVariable UUID topicId) {

        Topic topic = this.topicRepository
                .findById(topicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));

        AnswerService answerService = new AnswerService(
                answerRepository, answerDTOMapper, userRepository, topicRepository
        );

        // Modifiez la logique pour filtrer les réponses par sujet
        List<AnswerDTO> answerDTOS = answerService.findByTopic(topic);
        return answerDTOS;
    }

    //Ok
    @GetMapping("/{receiverId}/{topicId}/receiver")
    public List<AnswerDTO> getAllByTopicByReceiver(
            @PathVariable UUID receiverId,
            @PathVariable UUID topicId) {
        User receiver = this.userRepository
                .findById(receiverId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));
        Topic topic = this.topicRepository
                .findById(topicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));

        AnswerService answerService = new AnswerService(
                answerRepository, answerDTOMapper, userRepository, topicRepository
        );
        List<AnswerDTO> answerDTOS = answerService.findAll();
        return answerDTOS;
    }

    //Ok
    @PostMapping("/{topicId}/{authorId}/create-answers")
    public ResponseEntity<Answer> createAnswer(
            @PathVariable UUID topicId,
            @PathVariable UUID authorId,
            @RequestBody @Validated Answer newAnswer) {

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        newAnswer.setTopic(topic);
        newAnswer.setAuthor(author);

        Answer createdAnswer = answerRepository.save(newAnswer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Answer updateAnswer(@PathVariable UUID id, @RequestBody Answer answerUpdated) {
        // Assurez-vous que la réponse existante est récupérée de la base de données
        Answer existingAnswer = this.answerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Answer not found with id: " + id));

        // Mettez à jour les propriétés non nulles de existingAnswer avec celles de answerUpdated
        BeanUtils.copyNonNullProperties(answerUpdated, existingAnswer);

        // Enregistrez la réponse mise à jour dans la base de données
        return this.answerRepository.save(existingAnswer);
    }

    //Ok
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.answerRepository.deleteById(id);
    }

//Ok
    @DeleteMapping("/{id}/{authorId}/delete-by-author")
    public void deleteByAuthor(@PathVariable UUID id, @PathVariable UUID authorId) {
        User user = userRepository
                .findById(authorId)
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Not Found" + authorId));


        this.answerRepository.deleteById(id);

    }

}
