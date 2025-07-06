package com.cachemeifyoucan.econometro.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cachemeifyoucan.econometro.application.dto.CreateOfferRequest;
import com.cachemeifyoucan.econometro.application.dto.UpdateOfferRequest;
import com.cachemeifyoucan.econometro.domain.model.Offer;
import com.cachemeifyoucan.econometro.domain.service.OfferService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    @PostMapping
    @Operation(summary = "Create Offer", description = " Creates a offer with the provided information")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateOfferRequest request) {
        Offer offer = offerService.createOffer(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(offer.getId());
    }

    @PutMapping("/seller/{sellerId}/product/{productId}")
    @Operation(summary = "Update Offer", description = "Updates a offer with the provided information")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> update(long productId, long sellerId,
            @Valid @RequestBody UpdateOfferRequest request) {
        return ResponseEntity.ok(offerService.updateOffer(productId, sellerId, request));
    }
}
