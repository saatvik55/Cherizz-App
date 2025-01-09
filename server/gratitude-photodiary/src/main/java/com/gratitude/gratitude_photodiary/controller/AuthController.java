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

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody Map<String, Object> payload) {
        try {
            String email = (String) payload.get("email");
            String password = (String) payload.get("password");
            if (email == null || password == null) {
                return ResponseEntity.status(400).body("Email and password are required.");
            }
            String userId = authService.signupUser(email, password);
            Map<String, String> response = new HashMap<>();
            response.put("userId", userId);
            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(400).body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String password = payload.get("password");
            String userId = authService.loginUser(email, password);

            Map<String, String> response = new HashMap<>();
            response.put("userId", userId);
            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body("Invalid credentials: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String idToken) {
        try {
            FirebaseToken decodedToken = authService.verifyToken(idToken.substring(7)); // Remove "Bearer " prefix
            String userId = decodedToken.getUid();

            Map<String, String> response = new HashMap<>();
            response.put("userId", userId);
            response.put("email", decodedToken.getEmail());
            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body("Invalid token: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(authService.getUserById(userId));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(404).body("User not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            authService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(400).body("Error deleting user: " + e.getMessage());
        }
    }
}