package com.cachemeifyoucan.econometro.application.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cachemeifyoucan.econometro.application.dto.CreateProductRequest;
import com.cachemeifyoucan.econometro.application.dto.ProductDetailedResponse;
import com.cachemeifyoucan.econometro.application.dto.ProductResponse;
import com.cachemeifyoucan.econometro.application.dto.UpdateProductRequest;
import com.cachemeifyoucan.econometro.domain.model.Offer;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(request);
        URI location = URI.create("/products/" + product.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Product Detailed", description = "Retrieves a product with detailed information")
    public ResponseEntity<ProductDetailedResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductDetailed(id));
    }

    @GetMapping
    @Operation(summary = "Get All Products", description = "Retrieves all products")
    public ResponseEntity<List<ProductResponse>> getAll(@RequestParam(required = false) String query, @RequestParam(defaultValue = "0") long categoryId, @RequestParam(defaultValue = "0") long brandId) {

        List<ProductResponse> dto = productService.searchProducts(query, categoryId, brandId).stream()
                .map(product -> {
                    String image = product.getImages() == null || product.getImages().isEmpty()
                            ? null
                            : product.getImages().get(0).getContent();

                    Offer bestOffer = product.getBestOffer();
                    String seller = bestOffer == null ? null : bestOffer.getSeller().getName();
                    return new ProductResponse(
                            product.getId(),
                            product.getTitle(),
                            image,
                            product.getActiveOfferCount(),
                            product.getBestPrice(),
                            seller);
                })
                .toList();
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update Product", description = "Updates a product with the provided information")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody UpdateProductRequest request) {
        productService.updateProduct(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Product", description = "Deletes a product by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
