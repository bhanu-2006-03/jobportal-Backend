package com.jobnest.controller;

import com.jobnest.model.Job;
import com.jobnest.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for job listing CRUD and search/filter operations.
 */
@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobService jobService;

    /**
     * POST /api/jobs - Create a new job listing.
     */
    @PostMapping
    public ResponseEntity<?> createJob(@Valid @RequestBody Job job) {
        Job created = jobService.createJob(job);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Job created successfully");
        response.put("job", created);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/jobs - Get all active jobs.
     */
    @GetMapping
    public ResponseEntity<List<Job>> getAllActiveJobs() {
        return ResponseEntity.ok(jobService.getAllActiveJobs());
    }

    /**
     * GET /api/jobs/{id} - Get a single job by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Optional<Job> job = jobService.getJobById(id);
        if (job.isPresent()) {
            return ResponseEntity.ok(job.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", "Job not found");
        return ResponseEntity.status(404).body(error);
    }

    /**
     * GET /api/jobs/recruiter/{recruiterId} - Get all jobs by a recruiter.
     */
    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<Job>> getJobsByRecruiter(@PathVariable Long recruiterId) {
        return ResponseEntity.ok(jobService.getJobsByRecruiter(recruiterId));
    }

    /**
     * GET /api/jobs/search - Search and filter jobs.
     * Query params: keyword, location, jobType
     */
    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType) {
        return ResponseEntity.ok(jobService.searchJobs(keyword, location, jobType));
    }

    /**
     * PUT /api/jobs/{id} - Update an existing job.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateJob(
            @PathVariable Long id,
            @RequestBody Job job,
            @RequestParam Long recruiterId) {
        Map<String, Object> result = jobService.updateJob(id, job, recruiterId);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(403).body(result);
    }

    /**
     * DELETE /api/jobs/{id} - Delete a job listing.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteJob(
            @PathVariable Long id,
            @RequestParam Long recruiterId) {
        Map<String, Object> result = jobService.deleteJob(id, recruiterId);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(403).body(result);
    }

    /**
     * PATCH /api/jobs/{id}/toggle-status - Toggle job active/paused status.
     */
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Map<String, Object>> toggleJobStatus(
            @PathVariable Long id,
            @RequestParam Long recruiterId) {
        Map<String, Object> result = jobService.toggleJobStatus(id, recruiterId);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(403).body(result);
    }
}
