package com.chrisp1985.jobsboard_backend.service;

import com.chrisp1985.jobsboard_backend.model.job.JobApplication;
import com.chrisp1985.jobsboard_backend.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobRepo;

    public List<JobApplication> findJobList(Long userId) {
        return jobRepo.findByUserId(userId);
    }

    public Optional<JobApplication> findJob(Long userId) {
        return jobRepo.findById(userId);
    }

    public JobApplication storeJob(JobApplication jobApplication) {
        return jobRepo.save(jobApplication);
    }

    public void deleteJob(JobApplication jobApplication) {
        jobRepo.delete(jobApplication);
    }
}
