package com.gratitude.gratitude_photodiary.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath; // Path injected from application.properties or application.yml

    @PostConstruct
    public void init() {
        try {
            // Load the service account JSON from the resources folder
            InputStream serviceAccount = getClass()
                    .getClassLoader()
                    .getResourceAsStream(firebaseConfigPath);

            if (serviceAccount == null) {
                throw new IllegalStateException("Firebase service account file not found at: " + firebaseConfigPath);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized successfully!");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize Firebase. Check the configuration file path.", e);
        }
    }

    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore();
    }
}