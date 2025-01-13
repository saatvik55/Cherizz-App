package com.gratitude.gratitude_photodiary.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    private final Firestore db;

    public ImageService() {
        this.db = FirestoreClient.getFirestore();
    }

    public String addImage(String userId, String imageUrl) throws Exception {
        String imageId = db.collection("users")
                .document(userId)
                .collection("images")
                .document()
                .getId(); // Auto-generate imageId

        Map<String, Object> imageData = new HashMap<>();
        imageData.put("imageUrl", imageUrl);
        imageData.put("createdAt", System.currentTimeMillis());

        db.collection("users")
                .document(userId)
                .collection("images")
                .document(imageId)
                .set(imageData)
                .get();

        return imageId;
    }

    /**
     * Fetch all images for a specific user
     */
    public List<Map<String, Object>> getImages(String userId) throws Exception {
        List<Map<String, Object>> images = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection("users")
                .document(userId)
                .collection("images")
                .get();

        for (DocumentSnapshot document : future.get().getDocuments()) {
            Map<String, Object> imageData = document.getData();
            if (imageData != null) {
                imageData.put("imageId", document.getId()); // Include imageId in the response
                images.add(imageData);
            }
        }
        return images;
    }

    /**
     * Delete a specific image for a user
     */
    public String deleteImage(String userId, String imageId) throws Exception {
        db.collection("users")
                .document(userId)
                .collection("images")
                .document(imageId)
                .delete()
                .get();

        return "Image deleted successfully for user: " + userId;
    }
}
