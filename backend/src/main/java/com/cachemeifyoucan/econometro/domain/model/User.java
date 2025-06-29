package com.cachemeifyoucan.econometro.domain.model;

import com.cachemeifyoucan.econometro.domain.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    @NotBlank(message = "Password is required")
    @Nonnull
    private String password;
    @Nonnull
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
