package com.gratitude.gratitude_photodiary.service;

import com.gratitude.gratitude_photodiary.entity.User;
import com.gratitude.gratitude_photodiary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
