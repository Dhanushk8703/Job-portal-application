package com.jobportal.app.spring_rest_api.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobSeeker {
    @Id
    private String seekerId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private String phoneNumber;
    private String country;   
}
