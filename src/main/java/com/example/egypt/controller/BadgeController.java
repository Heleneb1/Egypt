package com.example.egypt.controller;

import com.example.egypt.DTO.BadgeDTO;
import com.example.egypt.DTOMapper.BadgeDTOMapper;

import com.example.egypt.entity.Badge;
import com.example.egypt.repository.BadgeRepository;

import com.example.egypt.services.BadgeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/badges")
public class BadgeController {
    private static BadgeRepository badgeRepository;
    private static BadgeDTOMapper badgeDTOMapper;

    BadgeController(BadgeRepository badgeRepository, BadgeDTOMapper badgeDTOMapper) {
        this.badgeRepository = badgeRepository;
        this.badgeDTOMapper = badgeDTOMapper;

    }

    @GetMapping
    public List<BadgeDTO> getAllBadges() {
        BadgeService badgeService = new BadgeService(
                badgeRepository, badgeDTOMapper);
        List<BadgeDTO> badgeDTOS = badgeService.findAll();

        return badgeDTOS;
    }

    @GetMapping("/{id}")
    public BadgeDTO getById(@PathVariable UUID id) {
        Badge badge = this.badgeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return badgeDTOMapper.convertToDTO(badge);

    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Badge create(@RequestBody Badge newBadge) {
        return this.badgeRepository.save(newBadge);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Badge update(@RequestBody Badge badgeUpdated) {
        return this.badgeRepository.save(badgeUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void delete(@PathVariable UUID id) {
        this.badgeRepository.deleteById(id);
    }

}
