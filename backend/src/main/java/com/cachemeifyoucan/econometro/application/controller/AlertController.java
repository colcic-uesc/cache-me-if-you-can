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

import com.cachemeifyoucan.econometro.application.dto.AlertRequest;
import com.cachemeifyoucan.econometro.application.dto.AlertResponse;
import com.cachemeifyoucan.econometro.domain.model.AlertId;
import com.cachemeifyoucan.econometro.domain.service.AlertService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    @PostMapping
    @Operation(summary = "Create Alert", description = " Creates a alert with the provided information")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> create(@Valid @RequestBody AlertRequest request) {
        alertService.createAlert(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get Alerts", description = "Retrieves all alerts for the current user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AlertResponse>> getById() {
        return ResponseEntity.ok(alertService.getAllAlertsForUser());
    }

    @PutMapping
    @Operation(summary = "Update Alert", description = "Updates a alert with the provided information")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> update(@Valid @RequestBody AlertRequest request) {
        alertService.updateAlert(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete Alert", description = "Deletes an alert by ID")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delete(long productId) {
        alertService.deleteAlert(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
