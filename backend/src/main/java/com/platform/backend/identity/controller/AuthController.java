package com.platform.backend.identity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.backend.identity.dto.AuthResponse;
import com.platform.backend.identity.dto.LoginRequest;
import com.platform.backend.identity.dto.RegisterRequest;
import com.platform.backend.identity.service.IdentityService;
import com.platform.backend.security.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IdentityService identityService;
    private final JwtService jwtService;

    public AuthController(
            IdentityService identityService,
            JwtService jwtService
    ) {
        this.identityService = identityService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        identityService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    @PostMapping("/login")
public ResponseEntity<AuthResponse> login(
        @Valid @RequestBody LoginRequest request
) {
    AuthResponse response = identityService.authenticateUser(request);
    return ResponseEntity.ok(response);
}
}
