package com.cachemeifyoucan.econometro.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cachemeifyoucan.econometro.application.dto.CreateOfferRequest;
import com.cachemeifyoucan.econometro.application.dto.OfferResponse;
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
        offerService.createOffer(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get Offers", description = "Retrieves all offers for the current seller")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<OfferResponse>> getById() {
        return ResponseEntity.ok(offerService.getAllOffersFromSeller());
    }

    @PutMapping
    @Operation(summary = "Update Offer", description = "Updates a offer with the provided information")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateOfferRequest request) {
        offerService.updateOffer(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
