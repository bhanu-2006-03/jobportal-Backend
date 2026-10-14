package com.jobnest.service;

import com.jobnest.model.User;
import com.jobnest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for user registration, login, and profile management.
 */
@Service
@SuppressWarnings("null")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Register a new user with hashed password.
     */
    public Map<String, Object> registerUser(User user) {
        Map<String, Object> response = new HashMap<>();

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            response.put("success", false);
            response.put("message", "Email is already registered");
            return response;
        }

        // Validate role
        String role = user.getRole().toUpperCase();
        if (!role.equals("SEEKER") && !role.equals("RECRUITER")) {
            response.put("success", false);
            response.put("message", "Role must be SEEKER or RECRUITER");
            return response;
        }
        user.setRole(role);

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        response.put("success", true);
        response.put("message", "Registration successful");
        response.put("user", sanitizeUser(savedUser));
        return response;
    }

    /**
     * Authenticate user login with password verification.
     */
    public Map<String, Object> loginUser(String email, String password) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return response;
        }

        User user = optionalUser.get();

        // Verify hashed password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return response;
        }

        response.put("success", true);
        response.put("message", "Login successful");
        response.put("user", sanitizeUser(user));
        return response;
    }

    /**
     * Get user by ID.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Get user by email.
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Update user profile.
     */
    public Map<String, Object> updateUser(Long id, User updatedUser) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        User user = optionalUser.get();
        if (updatedUser.getFullName() != null) user.setFullName(updatedUser.getFullName());
        if (updatedUser.getPhone() != null) user.setPhone(updatedUser.getPhone());
        if (updatedUser.getCompany() != null) user.setCompany(updatedUser.getCompany());

        User saved = userRepository.save(user);
        response.put("success", true);
        response.put("message", "Profile updated successfully");
        response.put("user", sanitizeUser(saved));
        return response;
    }

    /**
     * Remove password from user object before sending to client.
     */
    private Map<String, Object> sanitizeUser(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("fullName", user.getFullName());
        userMap.put("email", user.getEmail());
        userMap.put("role", user.getRole());
        userMap.put("company", user.getCompany());
        userMap.put("phone", user.getPhone());
        userMap.put("createdAt", user.getCreatedAt());
        return userMap;
    }
}
