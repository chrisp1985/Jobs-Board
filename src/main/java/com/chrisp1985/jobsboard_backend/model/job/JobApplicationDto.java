package com.chrisp1985.jobsboard_backend.model.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDto {

    private String company;
    private String title;
    private String status;
    private LocalDate appliedDate;
    private String notes;
    private String username;
}
