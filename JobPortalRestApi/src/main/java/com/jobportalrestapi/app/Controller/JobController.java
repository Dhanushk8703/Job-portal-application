package com.jobportalrestapi.app.Controller;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportalrestapi.app.model.Job;
import com.jobportalrestapi.app.model.JobDTO;
import com.jobportalrestapi.app.services.JobService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/employer/api")
@CrossOrigin(origins = "http://localhost:9070")
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

    @GetMapping("/posted")
    public List<JobDTO> getPostedJobs(@RequestParam String employerEmail) {
        return jobService.getPostedJobsByEmployer(employerEmail);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Job> postJobById(@PathVariable Long id) {
        // TODO: process PUT request

        Job job = jobService.postJobById(id);
        if (job != null) {
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJobById(@PathVariable Long id) {
        Job jobOptional = jobService.getJobById(id);
        if (jobOptional == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            jobService.deleteJobById(id);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (job != null) {
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}