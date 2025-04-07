package com.hireoscope.app.hiroscope_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.hireoscope.app.hiroscope_mvc.model.CompanyDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {

    private final RestTemplate restTemplate;

    public WebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String BASE_URL = "http://localhost:8081/api"; // Backend API URL

    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/employer_profile")
    public String employerProfilePage() {
        return "employer_profile";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/jobseeker_home")
    public String jobSeekerHomePage() {
        return "jobseeker_home";
    }

    @GetMapping("/register_role")
    public String employerPage() {
        return "register_role";
    }

    @GetMapping("/employer_register")
    public String employerRegisterPage(Model model) {
        model.addAttribute("company", new CompanyDTO());
        return "employer_register";
    }

    @GetMapping("/job_seeker")
    public String jobSeekerPage() {
        return "job_seeker";
    }

    @GetMapping("/jobsListing") 
    public String jobsListingPage() {
        return "jobsListing"; 
    }

    @GetMapping("/jobDescription")
    public String jobsDescriptionPage() {
        return "jobDescription"; 
    }

    @GetMapping("/employer_dashboard")
    public String employerDashboardPage() {
        return "employer_dashboard"; 
    }

    @GetMapping("/job_posting_form") 
    public String jobPostingFormPage() {
        return "job_posting_form"; 
    }

    @GetMapping("/jobseeker_register")
    public String jobSeekerRegisterPage() {
        return "jobseeker_register";
    }

    @GetMapping("/jobseeker_profile")
    public String jobSeekerProfilePage() {
        return "jobseeker_profile";
    }

    @PostMapping("/jobseeker/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String role,
                            Model model) {
        try {
            String url = BASE_URL + "/auth/login";
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
    
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("role", role);
    
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
    
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
    
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, String> responseBody = response.getBody();
                if ("Login successful".equals(responseBody.get("message"))) {
                    return "redirect:/jobseeker_home";
                } else {
                    model.addAttribute("error", "Incorrect email or password. Please double-check your credentials and try again.");
                    return "index"; // Return the login view with error message
                }
            }
    
            model.addAttribute("error", "Incorrect email or password. Please double-check your credentials and try again.");
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred during login.");
            return "index";
        }
    }
    

    @PostMapping("/employer/login")
    public String loginEmployer(@RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            Model model) {
        try {
            String url = BASE_URL + "/auth/login";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("role", role);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, String> responseBody = response.getBody();
                if ("Login successful".equals(responseBody.get("message"))) {
                    return "redirect:/employer_dashboard";
                } else {
                    model.addAttribute("error", "Incorrect email or password. Please double-check your credentials and try again.");
                    return "index";
                }
            }
            model.addAttribute("error", "Incorrect email or password. Please double-check your credentials and try again.");
             return "index"; 
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred during login.");
            return "index";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/auth/register")
    public String registerUser(@RequestParam String email,
            @RequestParam String password,
            @RequestParam String role) {
        try {
            String url = BASE_URL + "/auth/register";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("role", role);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/index";
            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }
}
