package com.example.egypt.DTOMapper;


import com.example.egypt.DTO.ArticleDTO;
import com.example.egypt.DTO.BadgeDTO;
import com.example.egypt.entity.Article;
import com.example.egypt.entity.Badge;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service

public class BadgeDTOMapper implements Function<Badge, BadgeDTO> {
    @Override
    public BadgeDTO apply(Badge badge) {
        return new BadgeDTO(
                badge.getId(),
                badge.getName(),
                badge.getDescription(),
                badge.getImage()
        );
    }
    public BadgeDTO convertToDTO(Badge badge) {
        return apply(badge);
    }
}
