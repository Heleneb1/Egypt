package com.example.egypt.controller;

import com.example.egypt.entity.Rating;
import com.example.egypt.repository.RatingRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private RatingRepository ratingRepository;

    RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @GetMapping
    public List<Rating> getAllRatings() {
        return this.ratingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Rating getById(@PathVariable UUID id) {
        return this.ratingRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public Rating create(@RequestBody Rating newRating) {
        return this.ratingRepository.save(newRating);
    }

    @PutMapping("/{id}")
    public Rating update(@PathVariable UUID id, @RequestBody Rating ratingUpdated) {
        ratingUpdated.setId(id);
        return this.ratingRepository.save(ratingUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.ratingRepository.deleteById(id);
    }
}