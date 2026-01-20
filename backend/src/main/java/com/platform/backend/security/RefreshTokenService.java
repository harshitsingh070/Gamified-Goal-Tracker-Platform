package com.platform.backend.security;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.platform.backend.identity.model.User;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusDays(30));
        return repository.save(token);
    }

    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken =
                repository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            repository.delete(refreshToken);
            throw new RuntimeException("Expired refresh token");
        }

        return refreshToken;
    }

    public void deleteByToken(String token) {
        repository.deleteByToken(token);
    }
}
