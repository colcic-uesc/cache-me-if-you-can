package com.cachemeifyoucan.econometro.domain.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.application.dto.CreateUserRequest;
import com.cachemeifyoucan.econometro.domain.enums.UserRole;
import com.cachemeifyoucan.econometro.domain.model.User;
import com.cachemeifyoucan.econometro.domain.repository.UserRepository;

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