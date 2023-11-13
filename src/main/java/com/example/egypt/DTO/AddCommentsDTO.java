package com.example.egypt.DTO;

import java.util.List;

public record AddCommentsDTO(
        List<String> comments) {
    @Override
    public List<String> comments() {
        return comments;
    }
}