package com.example.egypt.DTO;

import com.example.egypt.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record BadgeDTO(
        UUID id,
        String name,
        String description,
        String image,
        @JsonIgnore Set<User> user
) {
    public Set<UUID> getUsersIds() {
        return user.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}
