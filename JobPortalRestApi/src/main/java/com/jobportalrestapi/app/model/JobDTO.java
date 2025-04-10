package com.jobportalrestapi.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class JobDTO {
    public String employerEmail;
    public String jobTitle;
    public String jobDescription;
    public String workMode;
    public String location;
    public String eligibility;
    public String requiredSkills;
    public String experience;
    public String jobStatus;
    public String shift;
    public double minSalary;
    public double maxSalary;
    public LocalDate applicationDeadline;
}

