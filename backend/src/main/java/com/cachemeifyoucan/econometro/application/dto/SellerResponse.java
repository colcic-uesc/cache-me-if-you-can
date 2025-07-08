package com.cachemeifyoucan.econometro.application.dto;

public record SellerResponse(
    long id,
    String name,
    String cnpj,
    String imageContent,
    long managerId
) {

}
