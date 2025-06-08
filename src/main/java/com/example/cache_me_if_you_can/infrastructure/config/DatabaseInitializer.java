package com.example.cache_me_if_you_can.infrastructure.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.cache_me_if_you_can.application.dto.CreateUserRequest;
import com.example.cache_me_if_you_can.domain.enums.UserRole;
import com.example.cache_me_if_you_can.domain.model.User;
import com.example.cache_me_if_you_can.domain.repository.UserRepository;
import com.example.cache_me_if_you_can.domain.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        createUsers();
    }

    private void createUsers() {
        if (userRepository.count() > 0) {
            return;
        }
        List<CreateUserRequest> users = new ArrayList<>();
        users.add(new CreateUserRequest("Tainah", "taih.marques@uesc.br", "123"));
        users.add(new CreateUserRequest("Alice Johnson", "alice.johnson@example.com", "password123"));
        users.add(new CreateUserRequest("Bob Smith", "bob.smith@example.com", "securepass"));
        users.add(new CreateUserRequest("Charlie Lee", "charlie.lee@example.com", "charliepwd"));
        users.add(new CreateUserRequest("Diana Evans", "diana.evans@example.com", "diana2024"));
        users.add(new CreateUserRequest("Ethan Brown", "ethan.brown@example.com", "ethanpass"));

        for (CreateUserRequest user : users) {
            userService.createUser(user);
        }

    }
}
