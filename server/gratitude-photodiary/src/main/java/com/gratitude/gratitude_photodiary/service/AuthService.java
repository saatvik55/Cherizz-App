package com.gratitude.gratitude_photodiary.service;

import com.google.firebase.auth.*;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public String signupUser(String email, String password) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setEmailVerified(false)
                .setDisabled(false);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        return userRecord.getUid();
    }

    public String loginUser(String email, String password) throws FirebaseAuthException {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            UserRecord userRecord = auth.getUserByEmail(email);

            // Ensure the password is verified through a secure mechanism
            if (verifyPassword(userRecord, password)) {
                return userRecord.getUid();
            } else {
                throw new IllegalArgumentException("Invalid email or password");
            }
        } catch (FirebaseAuthException e) {
            System.err.println("Firebase error: " + e.getMessage());
            throw e; // Re-throw the Firebase-specific exception
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage(), e);
        }
    }

    private boolean verifyPassword(UserRecord userRecord, String password) {
        // Implement your password verification logic here
        return true; // Placeholder
    }

    public FirebaseToken verifyToken(String idToken) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }

    public UserRecord getUserById(String userId) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().getUser(userId);
    }

    public String generateCustomToken(String userId) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().createCustomToken(userId);
    }

    public void deleteUser(String userId) throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(userId);
    }
}