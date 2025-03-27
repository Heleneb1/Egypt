package com.example.egypt.repository;

import com.example.egypt.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TopicRepository extends JpaRepository<Topic, UUID> {
    // List<Topic> findByReceiver(User receiver);
    List<Topic> findAllByTagContainingIgnoreCase(String tag);
}
