package com.chrisp1985.jobsboard_backend.model.job;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    private String company;
    private String title;
    private String status;
    private LocalDate appliedDate;
    private String notes;
    private String username;
    private String userId;
}
