package com.platform.backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TempPasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin123"));
    }
}
