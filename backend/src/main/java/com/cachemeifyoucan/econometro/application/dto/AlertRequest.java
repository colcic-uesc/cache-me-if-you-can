package com.cachemeifyoucan.econometro.application.dto;

import java.math.BigDecimal;

public record AlertRequest( 
    BigDecimal desiredPrice, 
    long productId
) {

}
