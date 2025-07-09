package com.cachemeifyoucan.econometro.application.dto;

import java.math.BigDecimal;

public record OfferResponse(
    long productId,
    String productTitle,
    BigDecimal price,
    boolean enabled
) {

}
