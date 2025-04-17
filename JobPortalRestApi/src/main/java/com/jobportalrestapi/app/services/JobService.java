package com.jobportalrestapi.app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        job.setJobStatus("draft");
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
        dto.setId(job.getJobId());
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

    public Job postJobById(Long jobId) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setJobStatus("posted");
            return jobRepository.save(job);
        } else {
            throw new RuntimeException("Job not found");
        }

    }

    public Job draftJobById(Long jobId) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setJobStatus("draft");
            return jobRepository.save(job);
        } else {
            throw new RuntimeException("Job not found");
        }

    }

    public void deleteJobById(Long jobId) {
        Optional<Job> job = jobRepository.findById(jobId);
        if (job.isEmpty()) {
            throw new RuntimeException("Job not found");
        } else {
            jobRepository.deleteById(jobId);
        }
    }

    public Job getJobEntityById(Long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<JobDTO> getPostedJobsByEmployer(String employerEmail) {
        List<Job> draftJobs = jobRepository.findByEmployerEmailAndJobStatus(employerEmail, "posted");
        return draftJobs.stream().map(this::convertToDto).toList();
    }

    public JobDTO updateJobDetails(Long id, JobDTO updatedJobDto) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
    
            // Update fields
            job.setLocation(updatedJobDto.getLocation());
            job.setWorkMode(updatedJobDto.getWorkMode());
            job.setEligibility(updatedJobDto.getEligibility());
            job.setRequiredSkills(updatedJobDto.getRequiredSkills());
            job.setExperience(updatedJobDto.getExperience());
            job.setJobStatus(updatedJobDto.getJobStatus());
            job.setMinSalary(updatedJobDto.getMinSalary());
            job.setMaxSalary(updatedJobDto.getMaxSalary());
            job.setApplicationDeadline(updatedJobDto.getApplicationDeadline());
    
            jobRepository.save(job);
    
            // Manual DTO build
            JobDTO dto = new JobDTO();
            dto.setId(job.getJobId());
            dto.setLocation(job.getLocation());
            dto.setWorkMode(job.getWorkMode());
            dto.setEligibility(job.getEligibility());
            dto.setRequiredSkills(job.getRequiredSkills());
            dto.setExperience(job.getExperience());
            dto.setJobStatus(job.getJobStatus());
            dto.setMinSalary(job.getMinSalary());
            dto.setMaxSalary(job.getMaxSalary());
            dto.setApplicationDeadline(job.getApplicationDeadline());
            return dto;
        }
        return null;
    }
    

    public JobDTO getJobById(Long id) {
        return jobRepository.findById(id).map(job -> {
            JobDTO dto = new JobDTO();
            dto.setId(job.getJobId());
            dto.setLocation(job.getLocation());
            dto.setWorkMode(job.getWorkMode());
            dto.setEligibility(job.getEligibility());
            dto.setRequiredSkills(job.getRequiredSkills());
            dto.setExperience(job.getExperience());
            dto.setJobStatus(job.getJobStatus());
            dto.setMinSalary(job.getMinSalary());
            dto.setMaxSalary(job.getMaxSalary());
            dto.setApplicationDeadline(job.getApplicationDeadline());
            return dto;
        }).orElse(null);
    }
    

}
