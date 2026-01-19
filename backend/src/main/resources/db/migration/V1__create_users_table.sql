-- -----------------------------------------------------------------------------
-- PART 1: IDENTITY & ACCESS CONTROL
-- Table: users
-- Context: Development Initialization (Use Flyway/Liquibase for Prod)
-- -----------------------------------------------------------------------------


CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- IDENTITY
    -- Application Layer Requirement: Must lowercase email before inserting.
    email VARCHAR(150) NOT NULL,
    
    -- SECURITY
    -- 255 chars fits BCrypt ($2a$...), Argon2, etc.
    password_hash VARCHAR(255) NOT NULL,
    
    -- AUTHORIZATION
    role VARCHAR(20) NOT NULL,
    
    -- STATUS & SAFETY
    account_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    privacy_profile VARCHAR(20) NOT NULL DEFAULT 'PRIVATE',
    
    -- TRUST SCORE (Security metric)
    trust_score INT NOT NULL DEFAULT 10,
    
    -- AUDIT
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- CONSTRAINTS & INDEXES
    CONSTRAINT uq_users_email UNIQUE (email),
    
    -- DB-LEVEL TYPE SAFETY (MySQL 8.0.16+)
    -- Ensures no invalid strings enter the DB, keeping it synced with Java Enums.
    CONSTRAINT chk_users_role CHECK (role IN ('USER', 'ADMIN')),
    CONSTRAINT chk_users_status CHECK (account_status IN ('PENDING', 'ACTIVE', 'SUSPENDED', 'BANNED')),
    CONSTRAINT chk_users_privacy CHECK (privacy_profile IN ('PRIVATE', 'PUBLIC'))

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- VERIFICATION DATA
-- -----------------------------------------------------------------------------
-- Admin User: admin@platform.com / Password: admin123
INSERT INTO users (email, password_hash, role, account_status, privacy_profile, trust_score, created_at)
VALUES 
('admin@platform.com', '$2a$10$N.zmdr9k7uOCQb376ye.5.FY/g6.7.u8.9.10', 'ADMIN', 'ACTIVE', 'PRIVATE', 100, NOW());