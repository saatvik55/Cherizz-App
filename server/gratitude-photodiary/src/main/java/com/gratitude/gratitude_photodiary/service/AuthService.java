package com.gratitude.gratitude_photodiary.service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final Firestore firestore;

    public AuthService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String signupUser(Map<String, String> details) throws FirebaseAuthException {
        String email = details.get("email");
        String password = details.get("password");
        String firstName = details.get("first_name");
        String lastName = details.get("last_name");
        String phone = details.get("phone");

        // Create user in Firebase Authentication
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(firstName + " " + lastName)
                .setPhoneNumber(phone)
                .setEmailVerified(false)
                .setDisabled(false);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        String uid = userRecord.getUid();

        Map<String, Object> userDoc = new HashMap<>();
        userDoc.put("firstName", firstName);
        userDoc.put("lastName", lastName);
        userDoc.put("email", email);
        userDoc.put("phone", phone);
        userDoc.put("displayName", firstName + " " + lastName);
        userDoc.put("emailVerified", false);
        userDoc.put("createdAt", System.currentTimeMillis()); // Timestamp

        firestore.collection("users").document(uid).set(userDoc);

        return uid;
    }

    public String loginUser(Map<String, String> details) throws FirebaseAuthException {
        try {

            String email = details.get("email");
            String password = details.get("password");
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