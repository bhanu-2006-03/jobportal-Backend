package com.jobnest.service;

import com.jobnest.model.Job;
import com.jobnest.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for job listing CRUD and search/filter operations.
 */
@Service
@SuppressWarnings("null")
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    /**
     * Create a new job listing.
     */
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    /**
     * Get all active job listings.
     */
    public List<Job> getAllActiveJobs() {
        return jobRepository.findByIsActiveTrue();
    }

    /**
     * Get all jobs (including inactive) - for admin purposes.
     */
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    /**
     * Get a job by its ID.
     */
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    /**
     * Get all jobs posted by a specific recruiter.
     */
    public List<Job> getJobsByRecruiter(Long recruiterId) {
        return jobRepository.findByPostedBy(recruiterId);
    }

    /**
     * Update an existing job listing.
     */
    public Map<String, Object> updateJob(Long id, Job updatedJob, Long recruiterId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Job> optionalJob = jobRepository.findById(id);

        if (optionalJob.isEmpty()) {
            response.put("success", false);
            response.put("message", "Job not found");
            return response;
        }

        Job job = optionalJob.get();

        // Check if the recruiter owns this job
        if (!job.getPostedBy().equals(recruiterId)) {
            response.put("success", false);
            response.put("message", "You are not authorized to update this job");
            return response;
        }

        // Update fields
        if (updatedJob.getTitle() != null) job.setTitle(updatedJob.getTitle());
        if (updatedJob.getCompany() != null) job.setCompany(updatedJob.getCompany());
        if (updatedJob.getLocation() != null) job.setLocation(updatedJob.getLocation());
        if (updatedJob.getJobType() != null) job.setJobType(updatedJob.getJobType());
        if (updatedJob.getDescription() != null) job.setDescription(updatedJob.getDescription());
        if (updatedJob.getRequirements() != null) job.setRequirements(updatedJob.getRequirements());
        if (updatedJob.getSalary() != null) job.setSalary(updatedJob.getSalary());
        if (updatedJob.getSalaryRange() != null) job.setSalaryRange(updatedJob.getSalaryRange());
        if (updatedJob.getExperience() != null) job.setExperience(updatedJob.getExperience());
        if (updatedJob.getSkills() != null) job.setSkills(updatedJob.getSkills());
        if (updatedJob.getIsActive() != null) job.setIsActive(updatedJob.getIsActive());

        Job savedJob = jobRepository.save(job);
        response.put("success", true);
        response.put("message", "Job updated successfully");
        response.put("job", savedJob);
        return response;
    }

    /**
     * Delete a job listing.
     */
    public Map<String, Object> deleteJob(Long id, Long recruiterId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Job> optionalJob = jobRepository.findById(id);

        if (optionalJob.isEmpty()) {
            response.put("success", false);
            response.put("message", "Job not found");
            return response;
        }

        Job job = optionalJob.get();
        if (!job.getPostedBy().equals(recruiterId)) {
            response.put("success", false);
            response.put("message", "You are not authorized to delete this job");
            return response;
        }

        jobRepository.deleteById(id);
        response.put("success", true);
        response.put("message", "Job deleted successfully");
        return response;
    }

    /**
     * Search and filter jobs by keyword, location, and/or type.
     */
    public List<Job> searchJobs(String keyword, String location, String jobType) {
        String trimmedKeyword = (keyword != null) ? keyword.trim() : "";
        String trimmedLocation = (location != null) ? location.trim() : "";
        String trimmedType = (jobType != null) ? jobType.trim() : "";

        boolean hasKeyword = !trimmedKeyword.isEmpty();
        boolean hasLocation = !trimmedLocation.isEmpty();
        boolean hasType = !trimmedType.isEmpty();

        if (hasKeyword && hasLocation && hasType) {
            return jobRepository.searchByKeywordLocationAndType(trimmedKeyword, trimmedLocation, trimmedType);
        } else if (hasKeyword && hasLocation) {
            return jobRepository.searchByKeywordAndLocation(trimmedKeyword, trimmedLocation);
        } else if (hasKeyword) {
            return jobRepository.searchByKeyword(trimmedKeyword);
        } else if (hasLocation && hasType) {
            return jobRepository.findByLocationAndType(trimmedLocation, trimmedType);
        } else if (hasLocation) {
            return jobRepository.findByLocationContainingIgnoreCase(trimmedLocation);
        } else if (hasType) {
            return jobRepository.findActiveByJobType(trimmedType);
        } else {
            return jobRepository.findByIsActiveTrue();
        }
    }

    /**
     * Toggle active/inactive status of a job listing.
     */
    public Map<String, Object> toggleJobStatus(Long jobId, Long recruiterId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Job> optionalJob = jobRepository.findById(jobId);

        if (optionalJob.isEmpty()) {
            response.put("success", false);
            response.put("message", "Job not found");
            return response;
        }

        Job job = optionalJob.get();
        if (!job.getPostedBy().equals(recruiterId)) {
            response.put("success", false);
            response.put("message", "You are not authorized to update this job");
            return response;
        }

        boolean newStatus = !(job.getIsActive() != null && job.getIsActive());
        job.setIsActive(newStatus);
        Job saved = jobRepository.save(job);

        response.put("success", true);
        response.put("message", newStatus ? "Job activated and open for applications" : "Job paused/closed");
        response.put("job", saved);
        return response;
    }
}
