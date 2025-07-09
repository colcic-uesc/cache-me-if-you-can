package com.cachemeifyoucan.econometro.domain.model;

import java.math.BigDecimal;

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
public class Alert {
    @EmbeddedId
    AlertId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @NotNull(message = "Desired price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Desired price must be higher than zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal desiredPrice;

    public Alert(BigDecimal price, User user, Product product) {
        this.id = new AlertId(product.getId(), user.getId());
        this.desiredPrice = price;
        this.product = product;
        this.user = user;
    }
}
