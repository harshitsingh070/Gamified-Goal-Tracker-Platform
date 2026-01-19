package com.platform.backend.identity.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.backend.identity.dto.AuthResponse;
import com.platform.backend.identity.dto.LoginRequest;
import com.platform.backend.identity.dto.RegisterRequest;
import com.platform.backend.identity.model.User;
import com.platform.backend.identity.repository.UserRepository;
import com.platform.backend.security.JwtService;

@Service
public class IdentityService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public IdentityService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // =========================
    // LOGIN (JWT ISSUED HERE)
    // =========================
    public AuthResponse authenticateUser(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }

    // =========================
    // REGISTER
    // =========================
    @Transactional
    public void registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);
        user.setAccountStatus(User.AccountStatus.ACTIVE);
        user.setPrivacyProfile("PUBLIC");
        user.setTrustScore(0);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
