package com.gratitude.gratitude_photodiary.controller;
import com.gratitude.gratitude_photodiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserDetails(@PathVariable String userId) {
        try {
            Map<String, Object> userDetails = userService.getUserDetails(userId);
            return ResponseEntity.ok(userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch user details"));
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addOrUpdateUser(
            @PathVariable String userId,
            @RequestBody Map<String, Object> userDetails) {
        try {
            String result = userService.addOrUpdateUser(userId, userDetails);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add/update user details");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        try {
            String result = userService.deleteUser(userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete user");
        }
    }
}
