package com.jobportalspringmvc.app.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class JobDTO {
    public Long id;
    public String employerEmail;
    public String jobTitle;
    public String jobDescription;
    public String workMode;
    public String location;
    public String eligibility;
    public String companyId;
    public String requiredSkills;
    public String experience;
    public String jobStatus;
    public double minSalary;
    public double maxSalary;
    public LocalDate applicationDeadline;
}