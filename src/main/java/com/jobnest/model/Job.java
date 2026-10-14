package com.jobnest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * Job entity representing a job listing posted by a recruiter.
 */
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Job title is required")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Company name is required")
    @Column(nullable = false, length = 150)
    private String company;

    @NotBlank(message = "Location is required")
    @Column(nullable = false, length = 100)
    private String location;

    @NotBlank(message = "Job type is required")
    @Column(name = "job_type", nullable = false, length = 30)
    private String jobType; // FULL_TIME, PART_TIME, INTERNSHIP

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Positive(message = "Salary must be positive")
    private Double salary;

    @Column(name = "salary_range", length = 50)
    private String salaryRange;

    @Column(length = 100)
    private String experience;

    @Column(length = 500)
    private String skills;

    @NotNull
    @Column(name = "posted_by", nullable = false)
    private Long postedBy; // recruiter user ID

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        this.postedAt = LocalDateTime.now();
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    // -- Constructors --

    public Job() {}

    public Job(String title, String company, String location, String jobType,
               String description, String requirements, Double salary,
               String salaryRange, String experience, String skills, Long postedBy) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.jobType = jobType;
        this.description = description;
        this.requirements = requirements;
        this.salary = salary;
        this.salaryRange = salaryRange;
        this.experience = experience;
        this.skills = skills;
        this.postedBy = postedBy;
    }

    // -- Getters and Setters --

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public Long getPostedBy() { return postedBy; }
    public void setPostedBy(Long postedBy) { this.postedBy = postedBy; }

    public LocalDateTime getPostedAt() { return postedAt; }
    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
