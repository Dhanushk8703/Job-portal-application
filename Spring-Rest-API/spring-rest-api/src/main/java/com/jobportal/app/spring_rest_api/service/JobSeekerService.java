package com.jobportal.app.spring_rest_api.service;

import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.app.spring_rest_api.model.JobSeeker;
import com.jobportal.app.spring_rest_api.repository.JobSeekerRepository;

@Service
public class JobSeekerService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    // public Plan savePlanId(Plan plan) {
    //     String nextId = generateNextPlanId();
    //     plan.setPlanId(nextId);
        
    //     // **Fetch and Assign Category**
    //     if (plan.getCategory() == null || plan.getCategory().getCategoryId() == null) {
    //         throw new IllegalArgumentException("Category ID is required");
    //     }
        
    //     Category category = categoryRepository.findById(plan.getCategory().getCategoryId())
    //         .orElseThrow(() -> new IllegalArgumentException("Category not found for ID: " + plan.getCategory().getCategoryId()));
        
    //     plan.setCategory(category);
    //     return planRepository.save(plan);
    // }

    // private String generateNextPlanId() {
    //     Optional<Plan> lastPlan = planRepository.findTopByOrderByPlanIdDesc();

    //     if (lastPlan.isPresent()) {
    //         String lastId = lastPlan.get().getPlanId(); // e.g., "mbplan005"
    //         int num = Integer.parseInt(lastId.substring(6)); // Extract "005" -> 5
    //         return String.format("mbplan%03d", num + 1); // Generate "mbplan006"
    //     } else {
    //         return "mbplan001"; // First entry
    //     }
    // }

    public JobSeeker saveJobSeeker(JobSeeker jobSeeker) {
        return jobSeekerRepository.save(jobSeeker);
    }
}

