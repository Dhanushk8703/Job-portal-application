package com.jobportalrestapi.app.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class WorkExperienceDTO {

    private String companyName;
    private String companySector;
    private String jobTitle;
    private String jobLocation;
    private String positionType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private String jobDescription;
}
