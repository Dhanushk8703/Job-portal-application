package com.jobportal.app.spring_rest_api.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_seekers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobSeeker {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dob;
    private String degree;
    private String specialization;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String university;
    private String institution;
    private String rollNumber;
    private int currentSemester;
    private String educationType;
    private double percentage;
    
    @Lob
    private byte[] marksheet;

    // Getters and Setters
}

