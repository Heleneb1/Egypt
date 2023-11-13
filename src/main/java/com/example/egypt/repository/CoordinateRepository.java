package com.example.egypt.repository;

import com.example.egypt.entity.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoordinateRepository extends JpaRepository<Coordinate, UUID> {
}
