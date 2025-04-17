package com.jobportalspringmvc.app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jobportalspringmvc.app.dto.CompanyDTO;
import com.jobportalspringmvc.app.dto.JobDTO;
import com.jobportalspringmvc.app.dto.UserDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployerController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.base.url}")
    private String baseUrl;

    @GetMapping("/employer/home")
    public String employerHome(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        if (!"EMPLOYER".equalsIgnoreCase((String) session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/login";
        }
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);
        return "employer-home";
    }

    @GetMapping("/employer/candidates")
    public String showEmployerCandidates(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!"EMPLOYER".equalsIgnoreCase((String) session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/login";
        }
        return "employer-candidates";
    }

    @GetMapping("/employer/myjobs")
    public String showEmployerMyJobs(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        if (!"EMPLOYER".equalsIgnoreCase((String) session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/login";
        }

        String email = (String) session.getAttribute("email");
        String token = (String) session.getAttribute("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JobDTO[]> draftResponse = restTemplate.exchange(
                baseUrl + "/employer/api/drafts?employerEmail=" + email,
                HttpMethod.GET,
                entity,
                JobDTO[].class);
        List<JobDTO> draftJobs = Arrays.asList(draftResponse.getBody());

        ResponseEntity<JobDTO[]> postedResponse = restTemplate.exchange(
                baseUrl + "/employer/api/posted?employerEmail=" + email,
                HttpMethod.GET,
                entity,
                JobDTO[].class);
        List<JobDTO> postedJobs = Arrays.asList(postedResponse.getBody());

        model.addAttribute("draftJobs", draftJobs);
        model.addAttribute("postedJobs", postedJobs);

        return "employer-myjobs";
    }

    @GetMapping("/employer/postjob")
    public String showJobPostingForm(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        if (!"EMPLOYER".equalsIgnoreCase((String) session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/login";
        }

        model.addAttribute("email", session.getAttribute("email"));
        model.addAttribute("jobDto", new JobDTO());
        return "job_posting_form";
    }

    @GetMapping("/employer/profile")
    public String getCompanyData(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String email = (String) session.getAttribute("email");

        if (!"EMPLOYER".equalsIgnoreCase((String) session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/login";
        }

        String token = (String) session.getAttribute("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            String companyUrl = baseUrl + "/employer/api/company/" + email;
            ResponseEntity<CompanyDTO> companyResponse = restTemplate.exchange(
                    companyUrl,
                    HttpMethod.GET,
                    entity,
                    CompanyDTO.class);

            if (companyResponse.getStatusCode() == HttpStatus.OK && companyResponse.getBody() != null) {
                model.addAttribute("company", companyResponse.getBody());
            } else {
                redirectAttributes.addFlashAttribute("error", "Company not found.");
                return "redirect:/employer/home";
            }

            String userUrl = baseUrl + "/employer/api/details/" + email;
            ResponseEntity<UserDTO> userResponse = restTemplate.exchange(
                    userUrl,
                    HttpMethod.GET,
                    entity,
                    UserDTO.class);

            if (userResponse.getStatusCode() == HttpStatus.OK && userResponse.getBody() != null) {
                UserDTO user = userResponse.getBody();
                model.addAttribute("firstName", user.getFirstName());
                model.addAttribute("lastName", user.getLastName());
                model.addAttribute("userEmail", user.getEmail());
                model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
            } else {
                redirectAttributes.addFlashAttribute("error", "User data not found.");
                return "redirect:/employer/home";
            }

            return "employer_profile";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to retrieve profile data.");
            return "redirect:/employer/home";
        }
    }

    @PostMapping("/employer/postjob")
    public String handleJobPosting(@ModelAttribute JobDTO jobDto,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!"EMPLOYER".equalsIgnoreCase((String) session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/login";
        }

        String token = (String) session.getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<JobDTO> request = new HttpEntity<>(jobDto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/employer/api/jobs", request, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            redirectAttributes.addFlashAttribute("message", "Job posted successfully!");
            return "redirect:/employer/myjobs";
        } else {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to post job. Backend returned: " + response.getStatusCode());
            return "redirect:/employer/postjob";
        }
    }

    @GetMapping("/employer/postjob/{id}")
    public String postJobById(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!"EMPLOYER".equalsIgnoreCase((String) session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/login";
        }

        String token = (String) session.getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<JobDTO> response = restTemplate.exchange(
                    baseUrl + "/employer/api/post/" + id,
                    HttpMethod.PUT,
                    entity,
                    JobDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                redirectAttributes.addFlashAttribute("message", "Job posted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to post job.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error posting job.");
        }

        return "redirect:/employer/myjobs";
    }

    @GetMapping("/employer/move-to-draft/{id}")
    public String moveToDraftById(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!"EMPLOYER".equalsIgnoreCase((String) session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/login";
        }

        String token = (String) session.getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<JobDTO> response = restTemplate.exchange(
                    baseUrl + "/employer/api/draft/" + id,
                    HttpMethod.PUT,
                    entity,
                    JobDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                redirectAttributes.addFlashAttribute("message", "Job moved to draft.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to move job to draft.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving job to draft.");
        }

        return "redirect:/employer/myjobs";
    }

    @GetMapping("/employer/editjob/{id}")
public String showEditForm(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    String token = (String) session.getAttribute("token");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + token);

    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<JobDTO> response = restTemplate.exchange(
        baseUrl + "/employer/api/job/" + id,
        HttpMethod.GET,
        entity,
        JobDTO.class
    );

    if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
        model.addAttribute("jobDto", response.getBody());
        return "job_edit_form";
    } else {
        redirectAttributes.addFlashAttribute("error", "Failed to load job for editing.");
        return "redirect:/employer/myjobs";
    }
}

@PostMapping("/employer/editjob")
public String updateJob(@ModelAttribute JobDTO jobDto, HttpSession session, RedirectAttributes redirectAttributes) {
    String token = (String) session.getAttribute("token");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + token);

    HttpEntity<JobDTO> request = new HttpEntity<>(jobDto, headers);

    ResponseEntity<JobDTO> response = restTemplate.exchange(
        baseUrl + "/employer/api/job/" + jobDto.getId(),
        HttpMethod.PUT,
        request,
        JobDTO.class
    );

    if (response.getStatusCode() == HttpStatus.OK) {
        redirectAttributes.addFlashAttribute("message", "Job updated successfully!");
    } else {
        redirectAttributes.addFlashAttribute("error", "Failed to update job.");
    }

    return "redirect:/employer/myjobs";
}

}
