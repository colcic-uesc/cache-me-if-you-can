package com.cachemeifyoucan.econometro.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SellerRequest(
        @Size(max = 50, message = "Name must have up to 50 characters") @NotBlank(message = "Name cannot be blank") String name,

        @NotBlank(message = "Seller must have CNPJ") String cnpj,
        @NotNull(message = "Manager is required") long managerId,
        String imageContent) {

}
