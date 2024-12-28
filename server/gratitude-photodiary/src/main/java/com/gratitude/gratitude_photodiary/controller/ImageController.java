package com.gratitude.gratitude_photodiary.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getImagesByUserId(@PathVariable String userId) {
        try {
            Firestore firestore = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> query = firestore.collection("images")
                    .whereEqualTo("userId", userId)
                    .orderBy("timestamp")
                    .get();

            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            List<Map<String, Object>> images = documents.stream()
                    .map(QueryDocumentSnapshot::getData)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(images);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam("userId") String userId) {
        try {
            // Convert the file to Base64
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

            // Save the Base64 string and metadata to Firestore
            Firestore firestore = FirestoreClient.getFirestore();
            Map<String, Object> imageData = new     HashMap<>();
            imageData.put("userId", userId);
            imageData.put("imageBase64", base64Image);
            imageData.put("fileName", file.getOriginalFilename());
            imageData.put("timestamp", System.currentTimeMillis());

            String documentId = UUID.randomUUID().toString();
            firestore.collection("images").document(documentId).set(imageData);

            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Image upload failed!");
        }
    }
}
