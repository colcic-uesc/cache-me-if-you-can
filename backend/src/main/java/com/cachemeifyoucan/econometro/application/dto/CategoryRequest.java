package com.cachemeifyoucan.econometro.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @Size(max = 50, message = "Name must have up to 50 characters")
    @NotBlank(message = "Name cannot be blank")
    String name,
    long parentId
) {

    public CategoryRequest(String name) {
        this(name, 0);
    }
}
