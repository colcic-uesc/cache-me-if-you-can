package com.cachemeifyoucan.econometro.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 50, message = "Title must have up to 50 characters")
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Size(max = 500, message = "Description must have up to 500 characters")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Column(name = "released")
    @NotNull(message = "Release date cannot be null")
    private LocalDate released;

    @Column(name = "is_active")
    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @NotNull(message = "Brand is required")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category is required")
    @JsonIgnoreProperties("parent")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties({ "product", "id" })
    private List<Offer> offers;

    public Product(String title, String description, LocalDate released, Brand brand,
            Category category, List<Image> images) {
        this.title = title;
        this.description = description;
        this.released = released;
        this.brand = brand;
        this.category = category;
        this.images = images;
    }

    public BigDecimal getBestPrice() {
        if (offers == null) {
            return null;
        }
        return offers.stream()
                .filter(Offer::isEnabled)
                .map(Offer::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(null);
    }

}
