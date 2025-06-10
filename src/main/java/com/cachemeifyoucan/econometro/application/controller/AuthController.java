package com.cachemeifyoucan.econometro.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cachemeifyoucan.econometro.application.dto.CreateUserRequest;
import com.cachemeifyoucan.econometro.application.dto.LoginRequest;
import com.cachemeifyoucan.econometro.domain.service.UserService;
import com.cachemeifyoucan.econometro.infrastructure.security.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates a user, returning a JWT for later authorization")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.email(),
                request.password());

        Authentication authentication = authenticationManager.authenticate(authToken);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @Operation(summary = "Create user", description = "Creates a user with the provided information")
    public ResponseEntity<Void> create(@RequestBody CreateUserRequest user) {
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}