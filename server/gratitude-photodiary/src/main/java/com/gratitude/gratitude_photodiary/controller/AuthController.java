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
    public ResponseEntity<?> signupUser(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String password = payload.get("password");
            String firstName = payload.get("first_name");
            String lastName = payload.get("last_name");
            String phone = payload.get("phone");

            if (email == null || password == null || firstName == null || lastName == null || phone == null) {
                return ResponseEntity.status(400).body("All fields are required.");
            }

            String userId = authService.signupUser(payload);
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
            String userId = authService.loginUser(payload);

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