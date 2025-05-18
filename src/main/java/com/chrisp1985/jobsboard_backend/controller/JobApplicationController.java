package com.chrisp1985.jobsboard_backend.controller;

import com.chrisp1985.jobsboard_backend.model.job.JobApplication;
import com.chrisp1985.jobsboard_backend.model.job.JobApplicationDto;
import com.chrisp1985.jobsboard_backend.repository.JobApplicationRepository;
import com.chrisp1985.jobsboard_backend.service.JobApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    private JobApplicationRepository jobApplicationRepository;

    @GetMapping
    public List<JobApplication> list(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username"); // or "email" or another field
        String userId = jwt.getSubject(); // typically the UUID from Keycloak
        List<JobApplication> jobsFound = jobApplicationService.findJobList(userId);
        log.info("Getting jobs for {}. Found {} jobs.", username, jobsFound.size());
        return jobsFound;
    }

    @PostMapping
    public JobApplication create(@RequestBody JobApplicationDto job, @AuthenticationPrincipal Jwt jwt) {
        JobApplication jobApplication = jobApplicationService.createJobApplicationFromRequest(job, jwt);

        log.info("Creating job for {}, position: {}. Logged at: {}",
                job.getCompany(),
                job.getTitle(),
                job.getAppliedDate());

        return jobApplicationService.storeJob(jobApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody JobApplicationDto updatedJob, @AuthenticationPrincipal Jwt jwt) {
        return jobApplicationService.findJob(id).map(job -> {

            if (!job.getUsername().equals(jwt.getClaimAsString("preferred_username"))) {
                return ResponseEntity.status(403).build();
            }

            JobApplication jobApplication = jobApplicationService.createJobApplicationFromRequest(updatedJob, jwt);

            return ResponseEntity.ok(jobApplicationService.storeJob(jobApplication));

        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return jobApplicationService.findJob(id).map(job -> {
            if (!job.getUsername().equals(jwt.getClaimAsString("preferred_username"))) {
                return ResponseEntity.status(403).build();
            }
            jobApplicationService.deleteJob(job);

            return ResponseEntity.ok().build();

        }).orElse(ResponseEntity.notFound().build());
    }
}
