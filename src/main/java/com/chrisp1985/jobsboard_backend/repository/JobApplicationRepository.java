package com.chrisp1985.jobsboard_backend.repository;

import com.chrisp1985.jobsboard_backend.model.job.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, String> {
    List<JobApplication> findByUserId(String userId);
}
