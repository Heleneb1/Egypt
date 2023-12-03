package com.example.egypt.repository;

import com.example.egypt.entity.Answer;
import com.example.egypt.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository <Answer, UUID> {
    List<Answer> findByTopic(Topic topic);
//    List<Answer> findAllByAuthorAndTopic(User author, UUID topic);
}
