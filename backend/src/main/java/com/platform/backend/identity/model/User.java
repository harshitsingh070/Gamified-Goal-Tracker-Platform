package com.platform.backend.identity.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @Column(name = "privacy_profile", nullable = false)
    private String privacyProfile;

    @Column(name = "trust_score", nullable = false)
    private Integer trustScore;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // --------------------
    // ENUMS
    // --------------------
    public enum Role {
        USER,
        ADMIN
    }

    public enum AccountStatus {
        PENDING,
        ACTIVE,
        SUSPENDED,
        BANNED
    }
}
