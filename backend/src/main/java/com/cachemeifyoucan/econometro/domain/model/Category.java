package com.cachemeifyoucan.econometro.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 50, message = "Name must have up to 50 characters")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    public Category(String name) {
        this.name = name;
    }
}
