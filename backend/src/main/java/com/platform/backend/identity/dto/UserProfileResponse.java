package com.platform.backend.identity.dto;

import java.util.List;

public class UserProfileResponse {

    private final String email;
    private final String role;
    private final List<String> authorities;

    public UserProfileResponse(String email, String role, List<String> authorities) {
        this.email = email;
        this.role = role;
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
