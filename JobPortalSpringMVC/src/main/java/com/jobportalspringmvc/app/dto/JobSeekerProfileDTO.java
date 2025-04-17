package com.jobportalspringmvc.app.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class JobSeekerProfileDTO {
    
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dob;
}
