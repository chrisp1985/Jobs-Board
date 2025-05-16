package com.chrisp1985.jobsboard_backend.repository;

import com.chrisp1985.jobsboard_backend.model.job.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);
}
