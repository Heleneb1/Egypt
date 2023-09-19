package com.example.egypt.services;

import com.example.egypt.DTO.UserDTO;
import com.example.egypt.entity.User;
import com.example.egypt.DTOMapper.UserDTOMapper;
import com.example.egypt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private static UserRepository userRepository;
    private static UserDTOMapper userDTOMapper;

    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = UserService.userDTOMapper;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + username));
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userDTOMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public static Optional<UserDTO> findUserById(UUID id) {
        return userRepository.findById(id)
                .map(userDTOMapper::convertToDTO);
    }
}
