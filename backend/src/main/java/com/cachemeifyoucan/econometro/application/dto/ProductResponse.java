package com.cachemeifyoucan.econometro.application.dto;

import java.math.BigDecimal;

public record ProductResponse(
    long id,
    String title,
    String imageContent,
    BigDecimal bestPrice,
    String sellerName

) {

}
