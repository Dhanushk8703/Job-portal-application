package com.jobportalrestapi.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    private String employerEmail;
    private String jobTitle;

    @Column(columnDefinition = "TEXT")
    private String jobDescription;

    private String workMode;
    private String location;

    @Column(columnDefinition = "TEXT")
    private String eligibility;

    @Column(columnDefinition = "TEXT")
    private String requiredSkills;

    private String companyId;
    private String experience;

    private String jobStatus;

    private LocalDate applicationDeadline;
    private LocalDate jobPostedDate;

    private double minSalary;
    private double maxSalary;
}

