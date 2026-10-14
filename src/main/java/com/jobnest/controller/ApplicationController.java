package com.jobnest.controller;

import com.jobnest.model.Application;
import com.jobnest.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for job application operations.
 */
@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * POST /api/applications - Submit a new job application.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> applyForJob(@RequestBody Application application) {
        Map<String, Object> result = applicationService.applyForJob(application);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    /**
     * GET /api/applications/user/{userId} - Get all applications by a user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getApplicationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(applicationService.getApplicationsByUser(userId));
    }

    /**
     * GET /api/applications/job/{jobId} - Get all applications for a job.
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getApplicationsByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJob(jobId));
    }

    /**
     * PUT /api/applications/{id}/status - Update application status.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String status = body.get("status");
        if (status == null || status.isBlank()) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Status is required");
            return ResponseEntity.badRequest().body(error);
        }
        Map<String, Object> result = applicationService.updateApplicationStatus(id, status);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    /**
     * GET /api/applications/{id} - Get a single application by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/applications/count/{jobId} - Get application count for a job.
     */
    @GetMapping("/count/{jobId}")
    public ResponseEntity<Map<String, Object>> getApplicationCount(@PathVariable Long jobId) {
        Map<String, Object> response = new HashMap<>();
        response.put("jobId", jobId);
        response.put("count", applicationService.getApplicationCount(jobId));
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/applications/{id} - Withdraw/Delete an application by user.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> withdrawApplication(
            @PathVariable Long id,
            @RequestParam Long userId) {
        Map<String, Object> result = applicationService.withdrawApplication(id, userId);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    /**
     * PUT /api/applications/{id} - Update application details by applicant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateApplication(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody Application application) {
        Map<String, Object> result = applicationService.updateApplication(id, userId, application);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
}
