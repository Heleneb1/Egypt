package com.example.egypt.DTO;

import java.util.UUID;

public record BadgeDTO(
        UUID id,
        String name,
        String description,
        String image
) {
}
