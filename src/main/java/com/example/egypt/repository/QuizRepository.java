package com.example.egypt.repository;


import com.example.egypt.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    List<Quiz> findByAuthorId(UUID authorId);

    List<Quiz> findByTitleContainingIgnoreCase(String title);

}
