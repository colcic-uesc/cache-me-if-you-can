package com.example.cache_me_if_you_can.application.dto;

public record CreateUserRequest(
        String name,
        String email,
        String password) {
}