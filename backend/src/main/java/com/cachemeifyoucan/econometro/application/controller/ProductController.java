package com.cachemeifyoucan.econometro.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cachemeifyoucan.econometro.application.dto.CreateProductRequest;
import com.cachemeifyoucan.econometro.application.dto.UpdateProductRequest;
import com.cachemeifyoucan.econometro.domain.model.Product;
import com.cachemeifyoucan.econometro.domain.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create Product", description = " Creates a product with the provided information")
    public ResponseEntity<?> create(@Valid @RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/products/" + product.getId())
                .body(product.getId());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Product by ID", description = "Retrieves a product by its ID")
    public ResponseEntity<?> getById(long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    @Operation(summary = "Get All Products", description = "Retrieves all products")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Product", description = "Updates a product with the provided information")
    public ResponseEntity<?> update(long id, @Valid @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Product", description = "Deletes a product by its ID")
    public ResponseEntity<?> delete(long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
