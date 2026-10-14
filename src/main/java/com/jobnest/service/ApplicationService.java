package com.jobnest.service;

import com.jobnest.model.Application;
import com.jobnest.model.Job;
import com.jobnest.repository.ApplicationRepository;
import com.jobnest.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for job application submission and management.
 */
@Service
@SuppressWarnings("null")
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    /**
     * Submit a new job application.
     */
    public Map<String, Object> applyForJob(Application application) {
        Map<String, Object> response = new HashMap<>();

        // Check if user already applied to this job
        if (applicationRepository.existsByUserIdAndJobId(
                application.getUserId(), application.getJobId())) {
            response.put("success", false);
            response.put("message", "You have already applied for this job");
            return response;
        }

        // Fetch job details to store denormalized info
        Optional<Job> optionalJob = jobRepository.findById(application.getJobId());
        if (optionalJob.isEmpty()) {
            response.put("success", false);
            response.put("message", "Job not found");
            return response;
        }

        Job job = optionalJob.get();
        if (!job.getIsActive()) {
            response.put("success", false);
            response.put("message", "This job is no longer accepting applications");
            return response;
        }

        application.setJobTitle(job.getTitle());
        application.setJobCompany(job.getCompany());

        Application savedApplication = applicationRepository.save(application);
        response.put("success", true);
        response.put("message", "Application submitted successfully");
        response.put("application", savedApplication);
        return response;
    }

    /**
     * Get all applications submitted by a user.
     */
    public List<Application> getApplicationsByUser(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    /**
     * Get all applications for a specific job.
     */
    public List<Application> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    /**
     * Update the status of an application (by recruiter).
     */
    public Map<String, Object> updateApplicationStatus(Long applicationId, String status) {
        Map<String, Object> response = new HashMap<>();

        // Validate status
        List<String> validStatuses = Arrays.asList(
            "PENDING", "REVIEWED", "SHORTLISTED", "REJECTED", "ACCEPTED"
        );
        if (!validStatuses.contains(status.toUpperCase())) {
            response.put("success", false);
            response.put("message", "Invalid status. Must be one of: " + validStatuses);
            return response;
        }

        Optional<Application> optionalApp = applicationRepository.findById(applicationId);
        if (optionalApp.isEmpty()) {
            response.put("success", false);
            response.put("message", "Application not found");
            return response;
        }

        Application app = optionalApp.get();
        app.setStatus(status.toUpperCase());
        Application savedApp = applicationRepository.save(app);

        response.put("success", true);
        response.put("message", "Application status updated to " + status.toUpperCase());
        response.put("application", savedApp);
        return response;
    }

    /**
     * Get application count for a job.
     */
    public long getApplicationCount(Long jobId) {
        return applicationRepository.countByJobId(jobId);
    }

    /**
     * Get a single application by ID.
     */
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    /**
     * Withdraw/Delete an application by the applicant.
     */
    public Map<String, Object> withdrawApplication(Long applicationId, Long userId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Application> optionalApp = applicationRepository.findById(applicationId);

        if (optionalApp.isEmpty()) {
            response.put("success", false);
            response.put("message", "Application not found");
            return response;
        }

        Application app = optionalApp.get();
        if (!app.getUserId().equals(userId)) {
            response.put("success", false);
            response.put("message", "You are not authorized to withdraw this application");
            return response;
        }

        applicationRepository.deleteById(applicationId);
        response.put("success", true);
        response.put("message", "Application withdrawn successfully");
        return response;
    }

    /**
     * Update application cover letter or resume link by the applicant.
     */
    public Map<String, Object> updateApplication(Long applicationId, Long userId, Application updatedApp) {
        Map<String, Object> response = new HashMap<>();
        Optional<Application> optionalApp = applicationRepository.findById(applicationId);

        if (optionalApp.isEmpty()) {
            response.put("success", false);
            response.put("message", "Application not found");
            return response;
        }

        Application app = optionalApp.get();
        if (!app.getUserId().equals(userId)) {
            response.put("success", false);
            response.put("message", "You are not authorized to modify this application");
            return response;
        }

        if (updatedApp.getCoverLetter() != null) {
            app.setCoverLetter(updatedApp.getCoverLetter().trim());
        }
        if (updatedApp.getResumeLink() != null) {
            app.setResumeLink(updatedApp.getResumeLink().trim());
        }

        Application saved = applicationRepository.save(app);
        response.put("success", true);
        response.put("message", "Application updated successfully");
        response.put("application", saved);
        return response;
    }
}
