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

import com.cachemeifyoucan.econometro.application.dto.SellerRequest;
import com.cachemeifyoucan.econometro.domain.model.Seller;
import com.cachemeifyoucan.econometro.domain.service.SellerService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping
    @Operation(summary = "Create Seller", description = " Creates a seller with the provided information")
    public ResponseEntity<?> create(@Valid @RequestBody SellerRequest request) {
        Seller seller = sellerService.createSeller(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/sellers/" + seller.getId())
                .body(seller.getId());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Seller by ID", description = "Retrieves a seller by its ID")
    public ResponseEntity<?> getById(long id) {
        return ResponseEntity.ok(sellerService.getSellerById(id));
    }

    @GetMapping
    @Operation(summary = "Get All Sellers", description = "Retrieves all sellers")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Seller", description = "Updates a seller with the provided information")
    public ResponseEntity<?> update(long id, @Valid @RequestBody SellerRequest request) {
        return ResponseEntity.ok(sellerService.updateSeller(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Seller", description = "Deletes a seller by its ID")
    public ResponseEntity<?> delete(long id) {
        sellerService.deleteSeller(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
