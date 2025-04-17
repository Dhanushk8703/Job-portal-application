package com.jobportalspringmvc.app.controller;

import com.jobportalspringmvc.app.dto.CompanyDTO;
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
    public String showPendingCompanies(Model model) {
        String backendUrl = "http://localhost:9090/auth/admin/companies/pending";
        List<CompanyDTO> pendingCompanies = new ArrayList<>();

        try {
            ResponseEntity<List<CompanyDTO>> response = restTemplate.exchange(
                backendUrl,
                HttpMethod.GET,
                null,
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
    public String approveCompany(@PathVariable Long id) {
        String backendUrl = "http://localhost:9090/auth/admin/company/" + id + "/approve";

        try {
            restTemplate.postForEntity(backendUrl, null, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/admin/dashboard";
    }

    // ✅ POST: Reject a company
    @PostMapping("/companies/{id}/reject")
    public String rejectCompany(@PathVariable Long id) {
        String backendUrl = "http://localhost:9090/auth/admin/company/" + id + "/reject";

        try {
            restTemplate.postForEntity(backendUrl, null, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/admin/dashboard";
    }
}
