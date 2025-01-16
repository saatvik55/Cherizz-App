package com.gratitude.gratitude_photodiary.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.gratitude.gratitude_photodiary.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Sign up a new user.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody Map<String, String> payload) {
        try {
            validatePayload(payload, "email", "password", "first_name", "last_name", "phone");

            String userId = authService.signupUser(payload);

            Map<String, String> response = new HashMap<>();
            response.put("userId", userId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(400).body("Error registering user: " + e.getMessage());
        }
    }

    /**
     * Log in an existing user.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> payload) {
        try {
            validatePayload(payload, "email", "password");

            String userId = authService.loginUser(payload);

            Map<String, String> response = new HashMap<>();
            response.put("userId", userId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body("Invalid credentials: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void validatePayload(Map<String, String> payload, String... requiredFields) {
        for (String field : requiredFields) {
            if (payload.get(field) == null || payload.get(field).isEmpty()) {
                throw new IllegalArgumentException("Field '" + field + "' is required.");
            }
        }
    }
}
