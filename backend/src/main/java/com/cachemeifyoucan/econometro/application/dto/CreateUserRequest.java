package com.cachemeifyoucan.econometro.application.dto;

public record CreateUserRequest(
        String name,
        String email,
        String password) {
}