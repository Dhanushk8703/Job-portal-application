package com.jobportalrestapi.app.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportalrestapi.app.model.Job;
import com.jobportalrestapi.app.model.JobDTO;
import com.jobportalrestapi.app.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job createJob(JobDTO dto) {
        Job job = new Job();
        job.setEmployerEmail(dto.employerEmail);
        job.setJobTitle(dto.jobTitle);
        job.setJobDescription(dto.jobDescription);
        job.setWorkMode(dto.workMode);
        job.setLocation(dto.location);
        job.setEligibility(dto.eligibility);
        job.setRequiredSkills(dto.requiredSkills);
        job.setExperience(dto.experience);
        job.setJobStatus(dto.jobStatus);
        job.setApplicationDeadline(dto.applicationDeadline);
        job.setJobPostedDate(LocalDate.now());
        job.setMinSalary(dto.minSalary);
        job.setMaxSalary(dto.maxSalary);
        return jobRepository.save(job);
    }

    public List<JobDTO> getDraftJobsByEmployer(String employerEmail) {
        List<Job> draftJobs = jobRepository.findByEmployerEmailAndJobStatus(employerEmail, "draft");
        return draftJobs.stream().map(this::convertToDto).toList();
    }
    
    private JobDTO convertToDto(Job job) {
        JobDTO dto = new JobDTO();
        dto.setEmployerEmail(job.getEmployerEmail());
        dto.setJobTitle(job.getJobTitle());
        dto.setJobDescription(job.getJobDescription());
        dto.setWorkMode(job.getWorkMode());
        dto.setLocation(job.getLocation());
        dto.setEligibility(job.getEligibility());
        dto.setRequiredSkills(job.getRequiredSkills());
        dto.setExperience(job.getExperience());
        dto.setJobStatus(job.getJobStatus());
        dto.setMinSalary(job.getMinSalary());
        dto.setMaxSalary(job.getMaxSalary());
        dto.setApplicationDeadline(job.getApplicationDeadline());
        return dto;
    }
}

