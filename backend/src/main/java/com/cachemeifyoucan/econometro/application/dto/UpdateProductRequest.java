package com.cachemeifyoucan.econometro.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductRequest(
        @Size(max = 50, message = "Title must have up to 50 characters") @NotBlank(message = "Title cannot be blank") String title,

        @Size(max = 500, message = "Description must have up to 500 characters") @NotBlank(message = "Description cannot be blank") String description,

        @NotNull(message = "Brand is required") long brandId,

        @NotNull(message = "Category is required") @JsonIgnoreProperties("parent") long categoryId,

        @NotNull(message = "Status is required") Boolean enabled) {

}
