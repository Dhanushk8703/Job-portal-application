package com.jobportalrestapi.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportalrestapi.app.model.Job;
import com.jobportalrestapi.app.model.JobDTO;
import com.jobportalrestapi.app.services.JobService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/jobs")
    public ResponseEntity<Job> createJob(@RequestBody JobDTO jobDto) {

        Job job = jobService.createJob(jobDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(job);
    }

    @GetMapping("/drafts")
    public List<JobDTO> getDraftJobs(@RequestParam String employerEmail) {
        return jobService.getDraftJobsByEmployer(employerEmail);
    }

}
