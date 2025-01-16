package com.gratitude.gratitude_photodiary.service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class AuthService {

    private final Firestore firestore;

    @Value("${firebase.api_key}") // Firebase Web API Key
    private String firebaseApiKey;

    public AuthService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String signupUser(Map<String, String> details) throws FirebaseAuthException {
        String email = details.get("email");
        String password = details.get("password");
        String firstName = details.get("first_name");
        String lastName = details.get("last_name");
        String phone = details.get("phone");

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
        userDoc.put("userId", uid);
        userDoc.put("firstName", firstName);
        userDoc.put("lastName", lastName);
        userDoc.put("email", email);
        userDoc.put("phone", phone);
        userDoc.put("displayName", firstName + " " + lastName);
        userDoc.put("emailVerified", false);
        userDoc.put("createdAt", System.currentTimeMillis());

        firestore.collection("users").document(uid).set(userDoc);

        return uid;
    }

    public String loginUser(Map<String, String> details) throws Exception {
        String email = details.get("email");
        String password = details.get("password");
//        final credentials =  FirebaseAuth.getInstance().sign
        // Call Firebase REST API for signInWithEmailAndPassword
        String idToken = signInWithEmailAndPassword(email, password);
        if (idToken == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Retrieve user details from Firebase Authentication
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        return userRecord.getUid();
    }

    private String signInWithEmailAndPassword(String email, String password) throws Exception {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + firebaseApiKey;

        // Prepare JSON payload
        String payload = String.format(
                "{\"email\":\"%s\", \"password\":\"%s\", \"returnSecureToken\":true}",
                email, password
        );

        // Make HTTP POST request
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send payload
        try (OutputStream os = connection.getOutputStream()) {
            os.write(payload.getBytes());
            os.flush();
        }

        // Read the response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Extract idToken from response
            Map<String, Object> responseBody = new HashMap<>();
            responseBody = new com.fasterxml.jackson.databind.ObjectMapper().readValue(response.toString(), Map.class);
            return (String) responseBody.get("idToken");
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
