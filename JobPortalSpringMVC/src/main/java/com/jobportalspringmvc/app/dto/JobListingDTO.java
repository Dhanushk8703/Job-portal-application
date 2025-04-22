package com.jobportalspringmvc.app.dto;

import lombok.Data;

@Data
public class JobListingDTO {
    private JobDTO job;
    private CompanyDTO company;
}