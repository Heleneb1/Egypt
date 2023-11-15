package com.example.egypt.controller;

import com.example.egypt.entity.Coordinate;
import com.example.egypt.repository.CoordinateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/coordinates")
public class CoordinateController {

    private final CoordinateRepository coordinateRepository;

    // Constructor to inject CoordinateRepository
    public CoordinateController(CoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

    @GetMapping("")
    public List<Coordinate> getAll() {
        return this.coordinateRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coordinate> getById(@PathVariable UUID id) {
        Optional<Coordinate> coordinateOptional = coordinateRepository.findById(id);

        return coordinateOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Coordinate create(@RequestBody Coordinate newCoordinate) {
        Coordinate savedCoordinate = coordinateRepository.save(newCoordinate);

        // Return the saved coordinate
        return savedCoordinate;
    }

    @PutMapping("/{id}")
    public Coordinate update(@RequestBody Coordinate coordinateUpdated) {
        return this.coordinateRepository.save(coordinateUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.coordinateRepository.deleteById(id);
    }
}
