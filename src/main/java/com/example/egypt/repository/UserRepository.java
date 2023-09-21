package com.example.egypt.repository;

import com.example.egypt.entity.Role;
import com.example.egypt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String username);
 Optional<Object> findByRole(Role role);
}
