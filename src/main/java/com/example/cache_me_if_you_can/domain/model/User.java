package com.example.cache_me_if_you_can.domain.model;

import com.example.cache_me_if_you_can.domain.enums.UserRole;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Name cannot be blank")
    @Nonnull
    private String name;
    @NotBlank(message = "Email is required")
    @Nonnull
    private String email;
    @NotBlank(message = "Password is required")
    @Nonnull
    private String password;
    @Nonnull
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
