package com.platform.backend.identity.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.backend.identity.dto.UserProfileResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * Returns the logged-in user's profile.
     * Access: Any authenticated user (USER or ADMIN)
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse getMyProfile() {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        List<String> authorities = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String role = authorities.stream()
                .filter(a -> a.startsWith("ROLE_"))
                .findFirst()
                .orElse("UNKNOWN");

        return new UserProfileResponse(
                auth.getName(), // email from JWT subject
                role,
                authorities
        );
    }
}
