package com.gratitude.gratitude_photodiary.controller;

import com.gratitude.gratitude_photodiary.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(
            @PathVariable String userId,
            @RequestBody Map<String, String> payload) {

        String imageUrl = payload.get("imageUrl");

        if (imageUrl == null || imageUrl.isEmpty()) {
            return ResponseEntity.badRequest().body("Image URL is required");
        }

        try {
            String imageId = imageService.addImage(userId, imageUrl); // Generate imageId in service
            return ResponseEntity.ok(Map.of("message", "Image uploaded successfully!", "imageId", imageId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error uploading image", "details", e.getMessage()));
        }
    }

    /**
     * Get all images for a specific user.
     */
    @GetMapping
    public ResponseEntity<?> getImages(@PathVariable String userId) {
        try {
            List<Map<String, Object>> images = imageService.getImages(userId);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch images", "details", e.getMessage()));
        }
    }

    /**
     * Delete an image from a user's collection.
     */
    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(
            @PathVariable String userId,
            @PathVariable String imageId) {
        try {
            String result = imageService.deleteImage(userId, imageId);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete image", "details", e.getMessage()));
        }
    }
}
