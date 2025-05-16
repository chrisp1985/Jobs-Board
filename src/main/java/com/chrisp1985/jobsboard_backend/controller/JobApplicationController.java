package com.chrisp1985.jobsboard_backend.controller;

import com.chrisp1985.jobsboard_backend.model.job.JobApplication;
import com.chrisp1985.jobsboard_backend.model.job.JobApplicationDto;
import com.chrisp1985.jobsboard_backend.model.user.User;
import com.chrisp1985.jobsboard_backend.repository.JobApplicationRepository;
import com.chrisp1985.jobsboard_backend.repository.UserRepository;
import com.chrisp1985.jobsboard_backend.service.JobApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public List<JobApplication> list(Principal principal) {
        User user = userRepo.findByUsername(principal.getName());
        List<JobApplication> jobsFound = jobApplicationService.findJobList(user.getId());
        log.info("Getting jobs for {}. Found {} jobs.", user.getUsername(), jobsFound.size());
        return jobsFound;
    }

    @PostMapping
    public JobApplication create(@RequestBody JobApplicationDto job, Principal principal) {
        User user = userRepo.findByUsername(principal.getName());
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUsername(user.getUsername());
        jobApplication.setUserId(user.getId());
        jobApplication.setAppliedDate(LocalDate.now());
        jobApplication.setTitle(job.getTitle());
        jobApplication.setStatus(job.getStatus());
        jobApplication.setCompany(job.getCompany());
        jobApplication.setNotes(job.getNotes());

        log.info("Creating job for {}, position: {}. Logged at: {}",
                job.getCompany(),
                job.getTitle(),
                job.getAppliedDate());

        return jobApplicationService.storeJob(jobApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody JobApplication updatedJob, Principal principal) {
        return jobApplicationService.findJob(id).map(job -> {

            if (!job.getUsername().equals(principal.getName())) {
                return ResponseEntity.status(403).build();
            }

            JobApplication jobApplication = new JobApplication();
            jobApplication.setUserId(job.getUserId());
            jobApplication.setUsername(job.getUsername());
            jobApplication.setAppliedDate(LocalDate.now());
            jobApplication.setTitle(job.getTitle());
            jobApplication.setStatus(job.getStatus());
            jobApplication.setCompany(job.getCompany());
            jobApplication.setNotes(job.getNotes());

            return ResponseEntity.ok(jobApplicationService.storeJob(jobApplication));

        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        return jobApplicationService.findJob(id).map(job -> {
            if (!job.getUsername().equals(principal.getName())) {
                return ResponseEntity.status(403).build();
            }
            jobApplicationService.deleteJob(job);

            return ResponseEntity.ok().build();

        }).orElse(ResponseEntity.notFound().build());
    }
}
