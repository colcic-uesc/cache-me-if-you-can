package com.cachemeifyoucan.econometro.application.dto;

import java.math.BigDecimal;

public record AlertResponse( 
    long productId,
    String productTitle,
    BigDecimal desiredPrice,
    Offer fullfillingOffer
) {

     public static record Offer(long sellerId, String sellerName, String sellerImage, BigDecimal price) {

    }

}


