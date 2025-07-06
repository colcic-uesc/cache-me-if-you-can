package com.cachemeifyoucan.econometro.domain.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            throw new IllegalArgumentException("User with this email already exists");
        }

        User user = new User();

        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(encoder.encode(userDto.password()));
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    public void changeUserRole(String email, UserRole role) {
        User user = findByEmail(email);
        user.setRole(role);
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s doesn't exist.", email)));
    }

    public User getLoggedInUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No user logged in");
        }
        
        var details = (UserDetails) auth.getPrincipal();
        return findByEmail(details.getUsername());
    }

}