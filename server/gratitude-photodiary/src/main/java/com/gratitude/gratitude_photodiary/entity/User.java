package com.gratitude.gratitude_photodiary.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class User {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String displayName;
    private boolean emailVerified;
    private long createdAt;

    public User(String userId, String email, String firstName, String lastName, String phone,
                String displayName, boolean emailVerified, long createdAt) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.displayName = displayName;
        this.emailVerified = emailVerified;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", displayName='" + displayName + '\'' +
                ", emailVerified=" + emailVerified +
                ", createdAt=" + createdAt +
                '}';
    }
}
