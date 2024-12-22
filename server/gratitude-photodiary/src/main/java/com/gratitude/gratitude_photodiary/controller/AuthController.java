package com.gratitude.gratitude_photodiary.controller;

import com.gratitude.gratitude_photodiary.dto.AuthRequest;
import com.gratitude.gratitude_photodiary.entity.User;
import com.gratitude.gratitude_photodiary.repository.UserRepository;
import com.gratitude.gratitude_photodiary.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint to sign up a new user
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user to the repository
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Endpoint to log in the user and return a JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Generate JWT token
            String token = jwtUtil.generateToken(authentication.getName());

            // Return the token in a response
            return ResponseEntity.ok().body("JWT Token: " + token);
        } catch (AuthenticationException ex) {
            // Handle authentication failure
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
