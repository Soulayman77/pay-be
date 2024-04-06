package com.example.school.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


public class CustomPasswordMatcher implements PasswordEncoder {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder; // Optional for using matches()

    public CustomPasswordMatcher(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        // You can implement encoding here if needed, or delegate to passwordEncoder
        throw new UnsupportedOperationException("Encoding not needed");
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Extract username from request (assuming format: username:password)
        String username = rawPassword.toString().split(":")[0];
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return userDetails.getPassword().equals(encodedPassword);
    }

    private String getUsername(CharSequence rawPassword) {
        // Implement username extraction logic
        return "username_from_request";
    }
}