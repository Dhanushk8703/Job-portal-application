package com.jobportalrestapi.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobportalrestapi.app.model.JobSeekerDTO;
import com.jobportalrestapi.app.services.JobSeekerService;

@RestController
@RequestMapping("/api/jobseeker")
@CrossOrigin(origins = "*") // Optional: adjust for CORS policy
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerJobSeeker(@RequestBody JobSeekerDTO jobSeekerDTO) {
        try {
            jobSeekerService.registerJobSeeker(jobSeekerDTO);
            return ResponseEntity.ok("Job seeker registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error registering job seeker: " + e.getMessage());
        }
    }
}