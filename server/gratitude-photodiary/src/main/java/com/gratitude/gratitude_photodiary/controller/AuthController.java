package com.gratitude.gratitude_photodiary.controller;

import com.gratitude.gratitude_photodiary.dto.AuthRequest;
import com.gratitude.gratitude_photodiary.entity.User;
import com.gratitude.gratitude_photodiary.repository.UserRepository;
import com.gratitude.gratitude_photodiary.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint to sign up a new user.
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        // Encode the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Endpoint to log in a user and return a JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        // Retrieve user from database
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate the password
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // Return the token in JSON format
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
