package com.platform.backend.identity.dto;

public class AuthResponse {

    private String accessToken;
    private String email;
    private String role;

    public AuthResponse(String accessToken, String email, String role) {
        this.accessToken = accessToken;
        this.email = email;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
