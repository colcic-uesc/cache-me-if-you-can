package com.cachemeifyoucan.econometro.domain.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Offer {
    @EmbeddedId
    OfferId id;

    @JsonIgnoreProperties("image")
    @ManyToOne
    @MapsId("sellerId")
    @JoinColumn(name = "seller_id")
    Seller seller;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"images", "offers"})
    Product product;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be higher than zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "is_active")
    private boolean enabled = true;

    public Offer(BigDecimal price, Product product, Seller seller) {
        this.id = new OfferId(product.getId(), seller.getId());
        this.price = price;
        this.product = product;
        this.seller = seller;
    }

}
