package com.cachemeifyoucan.econometro.application.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
        @Size(max = 50, message = "Title must have up to 50 characters") @NotBlank(message = "Title cannot be blank") String title,

        @Size(max = 500, message = "Description must have up to 500 characters") @NotBlank(message = "Description cannot be blank") String description,

        @NotNull(message = "Release date is required") LocalDate released,

        @NotNull(message = "Brand is required") long brandId,

        @NotNull(message = "Category is required") long categoryId,

        List<String> images) {

}
