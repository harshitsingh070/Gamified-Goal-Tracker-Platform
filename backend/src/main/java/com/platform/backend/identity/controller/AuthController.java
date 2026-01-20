package com.platform.backend.identity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.backend.identity.dto.AuthResponse;
import com.platform.backend.identity.dto.LoginRequest;
import com.platform.backend.identity.dto.RegisterRequest;
import com.platform.backend.identity.model.User;
import com.platform.backend.identity.service.IdentityService;
import com.platform.backend.security.JwtService;
import com.platform.backend.security.RefreshToken;
import com.platform.backend.security.RefreshTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IdentityService identityService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(
            IdentityService identityService,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        this.identityService = identityService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        identityService.registerUser(request);
        return ResponseEntity.status(201).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        User user = identityService.authenticateUser(request);

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                new AuthResponse(
                        accessToken,
                        refreshToken.getToken(),
                        user.getEmail(),
                        user.getRole().name()
                )
        );
    }
}
