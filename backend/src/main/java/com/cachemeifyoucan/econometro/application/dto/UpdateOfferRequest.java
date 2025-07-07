package com.cachemeifyoucan.econometro.application.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record UpdateOfferRequest(
        @NotNull(message = "Product is required") long productId,
        
        @NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be higher than zero") BigDecimal price,

        @NotNull(message = "Status is required") Boolean enabled) {

}
