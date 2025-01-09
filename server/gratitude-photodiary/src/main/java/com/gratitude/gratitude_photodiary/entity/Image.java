package com.gratitude.gratitude_photodiary.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Image {

    private String id; // Firestore document ID
    private String userId;
    private String imageUrl;
    private LocalDateTime uploadedAt;

    public Image() {}

    public Image(String id, String userId, String imageUrl, LocalDateTime uploadedAt) {
        this.id = id;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.uploadedAt = uploadedAt;
    }

}
