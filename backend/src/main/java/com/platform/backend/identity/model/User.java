package com.platform.backend.identity.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    // ---------- ENUMS ----------
    public enum Role {
        USER, ADMIN
    }

    public enum AccountStatus {
        ACTIVE, SUSPENDED
    }

    // ---------- FIELDS ----------
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
    private int trustScore;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // ---------- GETTERS ----------
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public String getPrivacyProfile() {
        return privacyProfile;
    }

    public int getTrustScore() {
        return trustScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ---------- SETTERS ----------
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setPrivacyProfile(String privacyProfile) {
        this.privacyProfile = privacyProfile;
    }

    public void setTrustScore(int trustScore) {
        this.trustScore = trustScore;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
