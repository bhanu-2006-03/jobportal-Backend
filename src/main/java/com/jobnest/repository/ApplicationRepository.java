package com.jobnest.repository;

import com.jobnest.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Application entity database operations.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserId(Long userId);

    List<Application> findByJobId(Long jobId);

    Optional<Application> findByUserIdAndJobId(Long userId, Long jobId);

    boolean existsByUserIdAndJobId(Long userId, Long jobId);

    long countByJobId(Long jobId);
}
