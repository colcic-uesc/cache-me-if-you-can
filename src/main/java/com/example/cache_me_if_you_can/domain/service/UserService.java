package com.example.cache_me_if_you_can.domain.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cache_me_if_you_can.application.dto.CreateUserRequest;
import com.example.cache_me_if_you_can.domain.enums.UserRole;
import com.example.cache_me_if_you_can.domain.model.User;
import com.example.cache_me_if_you_can.domain.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder encoder;
    private UserRepository userRepository;

    public void createUser(CreateUserRequest userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        User user = new User();

        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(encoder.encode(userDto.password()));
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }
}