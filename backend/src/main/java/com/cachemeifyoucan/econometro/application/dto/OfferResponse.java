package com.cachemeifyoucan.econometro.application.dto;

import java.math.BigDecimal;

public record OfferResponse(
    long productId,
    String productTittle,
    BigDecimal price,
    boolean enabled
) {

}
