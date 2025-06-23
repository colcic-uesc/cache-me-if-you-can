package com.cachemeifyoucan.econometro.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    Product product;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be higher than zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "creation_date")
    @NotNull(message = "Date cannot be null")
    private LocalDate creationDate;

    public PriceHistory(Product product, BigDecimal price) {
        this.creationDate = LocalDate.now();
        this.product = product;
        this.price = price;
    }

    public PriceHistory(Product product, BigDecimal price, LocalDate date) {
        this.creationDate = date;
        this.product = product;
        this.price = price;
    }
}
