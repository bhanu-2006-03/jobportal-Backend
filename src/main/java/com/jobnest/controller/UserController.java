package com.jobnest.controller;

import com.jobnest.model.User;
import com.jobnest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for user registration, login, and profile operations.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * POST /api/users/register - Register a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody User user) {
        Map<String, Object> result = userService.registerUser(user);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    /**
     * POST /api/users/login - Authenticate a user.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        if (email == null || password == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Email and password are required");
            return ResponseEntity.badRequest().body(error);
        }

        Map<String, Object> result = userService.loginUser(email, password);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(401).body(result);
    }

    /**
     * GET /api/users/{id} - Get user profile by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            User u = user.get();
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", u.getId());
            userMap.put("fullName", u.getFullName());
            userMap.put("email", u.getEmail());
            userMap.put("role", u.getRole());
            userMap.put("company", u.getCompany());
            userMap.put("phone", u.getPhone());
            userMap.put("createdAt", u.getCreatedAt());
            return ResponseEntity.ok(userMap);
        }
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", "User not found");
        return ResponseEntity.status(404).body(error);
    }

    /**
     * PUT /api/users/{id} - Update user profile.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable Long id, @RequestBody User user) {
        Map<String, Object> result = userService.updateUser(id, user);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(404).body(result);
    }
}
