package com.example.egypt.repository;

import com.example.egypt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
//    Optional<Object> findByRole(Role role);
}
