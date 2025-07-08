package com.cachemeifyoucan.econometro.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cachemeifyoucan.econometro.application.dto.SellerRequest;
import com.cachemeifyoucan.econometro.application.dto.SellerResponse;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody SellerRequest request) {
        Seller seller = sellerService.createSeller(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/sellers/" + seller.getId())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Seller by ID", description = "Retrieves a seller by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById(long id) {
        Seller seller = sellerService.getSellerById(id);
        SellerResponse dto = new SellerResponse(seller.getId(), seller.getName(), seller.getCnpj(), seller.getImage().getContent(),
                        seller.getManager().getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get All Sellers", description = "Retrieves all sellers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        List<SellerResponse> dto = sellerService.getAllSellers().stream()
                .map(seller -> new SellerResponse(seller.getId(), seller.getName(), seller.getCnpj(), seller.getImage().getContent(),
                        seller.getManager().getId())).toList();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Seller", description = "Updates a seller with the provided information")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(long id, @Valid @RequestBody SellerRequest request) {
        sellerService.updateSeller(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Seller", description = "Deletes a seller by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(long id) {
        sellerService.deleteSeller(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
