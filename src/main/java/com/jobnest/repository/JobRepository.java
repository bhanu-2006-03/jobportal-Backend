package com.jobnest.repository;

import com.jobnest.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Job entity database operations.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByPostedBy(Long recruiterId);

    List<Job> findByIsActiveTrue();

    List<Job> findByJobType(String jobType);

    List<Job> findByLocationContainingIgnoreCase(String location);

    @Query("SELECT j FROM Job j WHERE j.isActive = true AND " +
           "(LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.skills) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Job> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT j FROM Job j WHERE j.isActive = true AND " +
           "(LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.skills) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Job> searchByKeywordAndLocation(@Param("keyword") String keyword,
                                         @Param("location") String location);

    @Query("SELECT j FROM Job j WHERE j.isActive = true AND " +
           "(LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.skills) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')) AND " +
           "j.jobType = :jobType")
    List<Job> searchByKeywordLocationAndType(@Param("keyword") String keyword,
                                             @Param("location") String location,
                                             @Param("jobType") String jobType);

    @Query("SELECT j FROM Job j WHERE j.isActive = true AND j.jobType = :jobType")
    List<Job> findActiveByJobType(@Param("jobType") String jobType);

    @Query("SELECT j FROM Job j WHERE j.isActive = true AND " +
           "LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')) AND " +
           "j.jobType = :jobType")
    List<Job> findByLocationAndType(@Param("location") String location,
                                    @Param("jobType") String jobType);
}
