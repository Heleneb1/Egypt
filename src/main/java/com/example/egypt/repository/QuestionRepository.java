package com.example.egypt.repository;

import com.example.egypt.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    List<Question> findByCategoryContaining(String category);
}
