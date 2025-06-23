package com.cachemeifyoucan.econometro.application.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
    @Size(max = 50, message = "Title must have up to 50 characters")
    @NotBlank(message = "Title cannot be blank")
    String title,

    @Size(max = 500, message = "Description must have up to 500 characters")
    @NotBlank(message = "Description cannot be blank")
    String description,

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be higher than zero")
    BigDecimal price,

    @Min(value = 0, message = "Stock quantity cannot be less than zero")
    Integer stockQuantity,

    @NotNull(message = "Brand is required")
    long brandId,
    
    @NotNull(message = "Category is required")
    long categoryId,


    List<String> images
) {

}
