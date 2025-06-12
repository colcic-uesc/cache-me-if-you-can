package com.cachemeifyoucan.econometro.application.dto;

import java.math.BigDecimal;

public record UpdateProductRequest(
    String title,
    String description,
    BigDecimal price,
    Integer stockQuantity,
    long brandId,
    long categoryId,
    Boolean enabled
) {

}
