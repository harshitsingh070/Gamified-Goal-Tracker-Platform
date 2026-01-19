package com.platform.backend.identity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platform.backend.identity.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used later for login
    Optional<User> findByEmail(String email);

    // Used for signup duplicate check
    boolean existsByEmail(String email);
}
