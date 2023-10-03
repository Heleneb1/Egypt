package com.example.egypt.services;

import com.example.egypt.DTO.BadgeDTO;
import com.example.egypt.DTOMapper.BadgeDTOMapper;

import com.example.egypt.entity.Badge;

import com.example.egypt.repository.BadgeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BadgeService {

    private static BadgeRepository badgeRepository;
    private static BadgeDTOMapper badgeDTOMapper;

    public BadgeService(BadgeRepository badgeRepository, BadgeDTOMapper badgeDTOMapper) {
        this.badgeRepository = badgeRepository;
        this.badgeDTOMapper = badgeDTOMapper;
    }

    public static Optional<BadgeDTO> findById(UUID id) {
        return badgeRepository.findById(id)
                .map(badgeDTOMapper::convertToDTO);
    }

    public List<BadgeDTO> findAll() {
        List<Badge> badges = badgeRepository.findAll();
        return badges.stream()
                .map(badgeDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    // public Badge createBadge(String name, String description, String image) {
    // Badge badge = new Badge();
    // badge.setName(name);
    // badge.setDescription(description);
    // badge.setImage(image);
    // return badgeRepository.save(badge);
    // }
}