package com.jobportalrestapi.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportalrestapi.app.model.Company;
import com.jobportalrestapi.app.model.Job;
import com.jobportalrestapi.app.model.JobListingDto;
import com.jobportalrestapi.app.repository.CompanyRepository;
import com.jobportalrestapi.app.repository.JobRepository;

@Service
public class JobListingService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public List<JobListingDto> getAllJobListings() {
        List<Job> jobs = jobRepository.findAll();
        List<JobListingDto> listings = new ArrayList<>();

        for (Job job : jobs) {
            Company company = companyRepository.findByEmployerEmail(job.getEmployerEmail());
            if (company != null) {
                listings.add(new JobListingDto(job, company));
            }
        }

        return listings;
    }

    public List<JobListingDto> getAllPostedJobsWithCompany() {
        List<Job> postedJobs = jobRepository.findByJobStatus("posted");
        List<JobListingDto> jobWithCompanyList = new ArrayList<>();

        for (Job job : postedJobs) {
            Company company = companyRepository.findByEmployerEmail(job.getEmployerEmail());
            JobListingDto dto = new JobListingDto(job, company);
            jobWithCompanyList.add(dto);
        }

        return jobWithCompanyList;
    }
}
