package com.jobportalspringmvc.app.controller;

import com.jobportalspringmvc.app.dto.CompanyDTO;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminFrontendController {

    private final RestTemplate restTemplate;

    public AdminFrontendController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ✅ GET: Show all pending companies
    @GetMapping("/dashboard")
    public String showPendingCompanies(Model model, HttpSession session) {
        // Retrieve token and role from session
        String token = (String) session.getAttribute("token");
        String role = (String) session.getAttribute("role");

        // Check if the user is an admin
        if (token == null || !role.equalsIgnoreCase("ADMIN")) {
            return "redirect:/login";  // Redirect to login page if not admin
        }

        String backendUrl = "http://localhost:9090/auth/admin/companies/pending";
        List<CompanyDTO> pendingCompanies = new ArrayList<>();

        try {
            // Set the token in the Authorization header
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<CompanyDTO>> response = restTemplate.exchange(
                backendUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CompanyDTO>>() {}
            );
            pendingCompanies = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        model.addAttribute("pendingCompanies", pendingCompanies);
        return "employer_managment"; // Your Thymeleaf template
    }

    // ✅ POST: Approve a company
    @PostMapping("/companies/{id}/approve")
    public String approveCompany(@PathVariable Long id, HttpSession session) {
        // Retrieve token and role from session
        String token = (String) session.getAttribute("token");
        String role = (String) session.getAttribute("role");

        // Check if the user is an admin
        if (token == null || !role.equalsIgnoreCase("ADMIN")) {
            return "redirect:/login";  // Redirect to login page if not admin
        }

        String backendUrl = "http://localhost:9090/auth/admin/company/" + id + "/approve";

        try {
            // Set the token in the Authorization header
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            restTemplate.exchange(backendUrl, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/admin/dashboard";
    }

    // ✅ POST: Reject a company
    @PostMapping("/companies/{id}/reject")
    public String rejectCompany(@PathVariable Long id, HttpSession session) {
        // Retrieve token and role from session
        String token = (String) session.getAttribute("token");
        String role = (String) session.getAttribute("role");

        // Check if the user is an admin
        if (token == null || !role.equalsIgnoreCase("ADMIN")) {
            return "redirect:/login";  // Redirect to login page if not admin
        }

        String backendUrl = "http://localhost:9090/auth/admin/company/" + id + "/reject";

        try {
            // Set the token in the Authorization header
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            restTemplate.exchange(backendUrl, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/admin/dashboard";
    }

    // Admin analytics page
    @GetMapping("/analytics")
    public String showAnalytics() {
        return "admin";
    }

    // Admin profile page
    @GetMapping("/profile")
    public String showProfile() {
        return "adminprofile";
    }

    // Query page
    @GetMapping("/query")
    public String showQueryPage() {
        return "query";
    }
}
