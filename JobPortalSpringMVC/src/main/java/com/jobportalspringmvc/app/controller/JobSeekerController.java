package com.jobportalspringmvc.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jobportalspringmvc.app.dto.EducationDTO;
import com.jobportalspringmvc.app.dto.JobDTO;
import com.jobportalspringmvc.app.dto.JobSeekerDTO;
import com.jobportalspringmvc.app.dto.SkillDTO;
import com.jobportalspringmvc.app.dto.WorkExperienceDTO;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class JobSeekerController {
    @Value("${api.base.url}")
    private String baseUrl;

    @GetMapping("/jobseeker/register")
    public String showJobPostingForm(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        // model.addAttribute("email", session.getAttribute("email"));

        // Create and initialize the JobSeekerDTO
        JobSeekerDTO jobSeekerDto = new JobSeekerDTO();
        jobSeekerDto.setEducationList(new ArrayList<>(List.of(new EducationDTO())));
        jobSeekerDto.setWorkExperienceList(new ArrayList<>(List.of(new WorkExperienceDTO())));
        jobSeekerDto.setSkillList(new ArrayList<>(List.of(new SkillDTO())));

        // Add the initialized object to the model
        model.addAttribute("JobSeekerDTO", jobSeekerDto);

        return "jobseeker_register"; // Return the correct view
    }

    @PostMapping("/jobseeker/register")
    public String handleJobSeekerForm(
            @ModelAttribute JobSeekerDTO jobSeekerDto,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // String email = (String) session.getAttribute("email");
        // jobSeekerDto.setEmail(email); // If needed

        String apiUrl = baseUrl + "/api/jobseeker/register";

        try {
            RestTemplate restTemplate = new RestTemplate();
            String token = (String) session.getAttribute("token");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<JobSeekerDTO> request = new HttpEntity<>(jobSeekerDto, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                redirectAttributes.addFlashAttribute("success", "Registration successful!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Registration failed. Try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "An error occurred while submitting the form.");
        }

        return "redirect:/jobseeker/register";
    }

    // @GetMapping("/jobseeker/profile")
    // public String showJobSeekerProfile(HttpSession session, Model model) {
    //     String apiUrl = baseUrl + "/api/jobseeker/profile";

    //     try {
    //         RestTemplate restTemplate = new RestTemplate();
    //         String token = (String) session.getAttribute("token");
    //         HttpHeaders headers = new HttpHeaders();
    //         headers.setContentType(MediaType.APPLICATION_JSON);
    //         headers.set("Authorization", "Bearer " + token);

    //         ResponseEntity<JobSeekerDTO> response = restTemplate.getForEntity(apiUrl, JobSeekerDTO.class, headers);

    //         if (response.getStatusCode().is2xxSuccessful()) {
    //             JobSeekerDTO jobSeekerDto = response.getBody();
    //             model.addAttribute("jobSeekerDto", jobSeekerDto);
    //         } else {
    //             model.addAttribute("error", "Failed to fetch profile data.");
    //         }

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         model.addAttribute("error", "An error occurred while fetching the profile data.");
    //     }

    //     return "jobseeker_profile";
    // }

    @GetMapping("/jobseeker/profile")
    public String getMethodName() {
        return "jobseeker"; // Return the correct view name
    }
    

}
