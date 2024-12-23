package com.gratitude.gratitude_photodiary.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data                       // Lombok: Generates getters, setters, toString, equals, hashCode, etc.
@NoArgsConstructor
@Table(name = "users") // Lombok: Generates a no-arguments constructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
}