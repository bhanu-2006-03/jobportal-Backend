package com.jobnest.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Application entity representing a job seeker's application to a job.
 */
@Entity
@Table(name = "applications",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "job_id"}))
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "applicant_name", length = 100)
    private String applicantName;

    @Column(name = "applicant_email", length = 150)
    private String applicantEmail;

    @Column(name = "cover_letter", columnDefinition = "TEXT")
    private String coverLetter;

    @Column(name = "resume_link", length = 500)
    private String resumeLink;

    @Column(nullable = false, length = 30)
    private String status = "PENDING"; // PENDING, REVIEWED, SHORTLISTED, REJECTED, ACCEPTED

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    // Store job info for easy display
    @Column(name = "job_title", length = 200)
    private String jobTitle;

    @Column(name = "job_company", length = 150)
    private String jobCompany;

    @PrePersist
    protected void onCreate() {
        this.appliedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    // -- Constructors --

    public Application() {}

    public Application(Long userId, Long jobId, String applicantName,
                       String applicantEmail, String coverLetter, String resumeLink,
                       String jobTitle, String jobCompany) {
        this.userId = userId;
        this.jobId = jobId;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.coverLetter = coverLetter;
        this.resumeLink = resumeLink;
        this.jobTitle = jobTitle;
        this.jobCompany = jobCompany;
    }

    // -- Getters and Setters --

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public String getApplicantEmail() { return applicantEmail; }
    public void setApplicantEmail(String applicantEmail) { this.applicantEmail = applicantEmail; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public String getResumeLink() { return resumeLink; }
    public void setResumeLink(String resumeLink) { this.resumeLink = resumeLink; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getJobCompany() { return jobCompany; }
    public void setJobCompany(String jobCompany) { this.jobCompany = jobCompany; }
}
