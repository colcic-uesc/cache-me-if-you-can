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

import com.cachemeifyoucan.econometro.application.dto.BrandRequest;
import com.cachemeifyoucan.econometro.domain.model.Brand;
import com.cachemeifyoucan.econometro.domain.service.BrandService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    @Operation(summary = "Create Brand", description = " Creates a brand with the provided information")
    public ResponseEntity<?> create(@RequestBody BrandRequest request) {
        Brand brand = brandService.createBrand(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/brands/" + brand.getId())
                .body(brand.getId());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Brand by ID", description = "Retrieves a brand by its ID")
    public ResponseEntity<?> getById(long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @GetMapping
    @Operation(summary = "Get All Brands", description = "Retrieves all brands")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Brand", description = "Updates a brand with the provided information")
    public ResponseEntity<?> update(long id, @RequestBody BrandRequest request) {
        return ResponseEntity.ok(brandService.updateBrand(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Brand", description = "Deletes a brand by its ID")
    public ResponseEntity<?> delete(long id) {
        brandService.deleteBrand(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
