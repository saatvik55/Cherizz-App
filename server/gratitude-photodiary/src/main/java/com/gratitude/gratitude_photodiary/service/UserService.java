package com.gratitude.gratitude_photodiary.service;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private final Firestore firestore;

    public UserService() {
        this.firestore = FirestoreOptions.getDefaultInstance().getService();
    }

    public Map<String, Object> getUserDetails(String userId) throws Exception {
        DocumentSnapshot document = firestore.collection("users").document(userId).get().get();

        if (!document.exists()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        return document.getData();
    }

    public String addOrUpdateUser(String userId, Map<String, Object> userDetails)
            throws ExecutionException, InterruptedException {
        firestore.collection("users").document(userId).set(userDetails).get();
        return "User details successfully added/updated for userId: " + userId;
    }

    public String deleteUser(String userId) throws ExecutionException, InterruptedException {
        firestore.collection("users").document(userId).delete().get();
        return "User successfully deleted with userId: " + userId;
    }
}
