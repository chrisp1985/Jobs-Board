package com.chrisp1985.jobsboard_backend.service;

import com.chrisp1985.jobsboard_backend.model.job.JobApplication;
import com.chrisp1985.jobsboard_backend.model.job.JobApplicationDto;
import com.chrisp1985.jobsboard_backend.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobRepo;

    public List<JobApplication> findJobList(String userId) {
        return jobRepo.findByUserId(userId);
    }

    public Optional<JobApplication> findJob(String userId) {
        return jobRepo.findById(userId);
    }

    public JobApplication storeJob(JobApplication jobApplication) {
        return jobRepo.save(jobApplication);
    }

    public void deleteJob(JobApplication jobApplication) {
        jobRepo.delete(jobApplication);
    }

    public JobApplication createJobApplicationFromRequest(JobApplicationDto job, Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        String userId = jwt.getSubject();
        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(UUID.randomUUID().toString());
        jobApplication.setUsername(username);
        jobApplication.setUserId(userId);
        jobApplication.setAppliedDate(LocalDate.now());
        jobApplication.setTitle(job.getTitle());
        jobApplication.setStatus(job.getStatus());
        jobApplication.setCompany(job.getCompany());
        jobApplication.setNotes(job.getNotes());
        return jobApplication;
    }

    public JobApplication createJobApplicationFromRequest(JobApplication job, Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        String userId = jwt.getSubject();
        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(UUID.randomUUID().toString());
        jobApplication.setUsername(username);
        jobApplication.setUserId(userId);
        jobApplication.setAppliedDate(LocalDate.now());
        jobApplication.setTitle(job.getTitle());
        jobApplication.setStatus(job.getStatus());
        jobApplication.setCompany(job.getCompany());
        jobApplication.setNotes(job.getNotes());
        return jobApplication;
    }
}
