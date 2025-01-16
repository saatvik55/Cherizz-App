package com.gratitude.gratitude_photodiary.controller;

import com.gratitude.gratitude_photodiary.service.ImageKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/imagekit")
public class ImageKitController {

    private final ImageKitService imageKitService;

    @Autowired
    public ImageKitController(ImageKitService imageKitService) {
        this.imageKitService = imageKitService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestBody Map<String, String> payload) {
        try {
            String base64File = payload.get("file");
            String fileName = payload.get("fileName");

            // Call the service to upload the image to ImageKit
            String imageUrl = imageKitService.uploadImageToImageKit(base64File, fileName);

            // Return the ImageKit URL
            Map<String, String> response = new HashMap<>();
            response.put("url", imageUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
