package com.cachemeifyoucan.econometro.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.cachemeifyoucan.econometro.domain.model.Brand;
import com.cachemeifyoucan.econometro.domain.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record ProductDetailedResponse(
    String title,
    String description,
    LocalDate released,
    Brand brand,

    @JsonIgnoreProperties("parent")
    Category category,
    BigDecimal bestPrice,
    List<String> images,
    List<Offer> offers,
    List<History> history
) implements Serializable {

    public static record Offer(long sellerId, String sellerName, String sellerImage, BigDecimal price) {

    }
    public static record History(LocalDate date, BigDecimal price) {

    }
}
