package com.gratitude.gratitude_photodiary.controller;

import com.gratitude.gratitude_photodiary.entity.Image;
import com.gratitude.gratitude_photodiary.service.ImageService;
import com.gratitude.gratitude_photodiary.util.FirebaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService, FirebaseUtil firebaseUtil) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestHeader("Authorization") String token, @RequestBody Image image) {
        try {
            // Extract userId from Firebase token
            String userId = FirebaseUtil.extractUserId(token.substring(7));
            image.setUserId(userId);

            // Save the image
            imageService.saveImage(image);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getImagesByUserId(@RequestHeader("Authorization") String token) {
        try {
            String userId = FirebaseUtil.extractUserId(token.substring(7));
            List<Image> images = imageService.getImagesByUserId(userId);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch images: " + e.getMessage());
        }
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable String imageId) {
        try {
            // Delete image
            imageService.deleteImage(imageId);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete image: " + e.getMessage());
        }
    }
}
