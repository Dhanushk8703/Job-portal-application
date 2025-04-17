package com.jobportalrestapi.app.model;

import com.jobportalrestapi.app.model.Job;
import com.jobportalrestapi.app.model.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobListingDto {
    private Job job;
    private Company company;
}