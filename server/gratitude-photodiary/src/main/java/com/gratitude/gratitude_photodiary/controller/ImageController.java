package com.gratitude.gratitude_photodiary.controller;

import com.gratitude.gratitude_photodiary.entity.Image;
import com.gratitude.gratitude_photodiary.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam String userId, @RequestBody Image image) {
        try {
            // Set userId to the image entity
            image.setUserId(userId);

            // Save the image
            imageService.saveImage(image);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getImages(@RequestParam String userId) {
        try {
            // Fetch images for the given userId
            List<Image> images = imageService.getImagesByUserId(userId);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch images: " + e.getMessage());
        }
    }

}
