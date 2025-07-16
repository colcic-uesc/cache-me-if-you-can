package com.cachemeifyoucan.econometro.application.dto;

import java.math.BigDecimal;

public record ProductResponse(
    long id,
    String title,
    String imageContent,
    int offers,
    BigDecimal bestPrice,
    String sellerName
) {

}
