package com.example.egypt.DTOMapper;

import com.example.egypt.DTO.UserDTO;
import com.example.egypt.entity.Badge;
import com.example.egypt.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getAvatar(),
                user.getBiography(),
                user.getLastname(),
                user.getFirstname(),
                user.getRole(),
                user.getEmail(),
                user.getComments(),
                user.getQuizzes(),
                user.getBadges()
        );
    }

    public  UserDTO convertToDTO(User user) {
        return apply (user);
    }

}
