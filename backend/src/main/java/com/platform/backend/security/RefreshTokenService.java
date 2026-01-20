package com.platform.backend.security;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.platform.backend.security.RefreshToken;
import com.platform.backend.identity.model.User;
import com.platform.backend.security.RefreshTokenRepository;

import com.platform.backend.identity.model.User;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(User user) {

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusDays(7));

        return refreshTokenRepository.save(token);
    }

    public RefreshToken verifyRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }
}
