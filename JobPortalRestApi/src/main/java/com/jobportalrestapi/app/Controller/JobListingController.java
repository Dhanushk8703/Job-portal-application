package com.jobportalrestapi.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportalrestapi.app.model.JobListingDto;
import com.jobportalrestapi.app.services.JobListingService;


@RestController
@RequestMapping("/api/jobs/jobseeker")
public class JobListingController {


    @Autowired
    private JobListingService jobListingService;

    @GetMapping("/listings")
    public List<JobListingDto> getJobListings() {
        return jobListingService.getAllJobListings();
    }
   
     @GetMapping("/listings/posted")
    public ResponseEntity<List<JobListingDto>> getPostedJobsWithCompany() {
        List<JobListingDto> result = jobListingService.getAllPostedJobsWithCompany();
        return ResponseEntity.ok(result);
    }
}
