package com.example.cache_me_if_you_can.infrastructure.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.cache_me_if_you_can.domain.enums.UserRole;
import com.example.cache_me_if_you_can.domain.model.User;
import com.example.cache_me_if_you_can.domain.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        createUsers();
    }

    private void createUsers() {
        if (userRepository.count() > 0) {
            return;
        }
        List<User> users = new ArrayList<>();
        users.add(new User("Tainah", "taih.marques@uesc.br", "123", UserRole.ADMIN));
        users.add(new User("Alice Johnson", "alice.johnson@example.com", "password123", UserRole.USER));
        users.add(new User("Bob Smith", "bob.smith@example.com", "securepass", UserRole.USER));
        users.add(new User("Charlie Lee", "charlie.lee@example.com", "charliepwd", UserRole.USER));
        users.add(new User("Diana Evans", "diana.evans@example.com", "diana2024", UserRole.USER));
        users.add(new User("Ethan Brown", "ethan.brown@example.com", "ethanpass", UserRole.USER));

        userRepository.saveAll(users);

    }
}
