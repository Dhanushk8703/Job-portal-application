package com.jobportalspringmvc.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.jobportalspringmvc.app.dto.CompanyDTO;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class CompanyController {

    @Value("${api.base.url}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/company-form")
    public String showCompanyForm(@RequestParam("email") String email, Model model) {
        model.addAttribute("company", new CompanyDTO());
        model.addAttribute("email", email);
        return "employer_register";
    }

    @PostMapping("/submit-company")
    public String submitCompany(@ModelAttribute CompanyDTO company,
            @RequestParam("logo") MultipartFile logoFile,
            @RequestParam("email") String employerEmail,
            Model model) {
        try {
            // 1. Use employerEmail from URL parameter
            if (employerEmail == null || employerEmail.isBlank()) {
                model.addAttribute("responseMessage", "Employer email not provided in request.");
                return "error-page";
            }
            company.setEmployerEmail(employerEmail);

            // 2. Handle logo file
            if (!logoFile.isEmpty()) {
                company.setLogo(logoFile);
                company.setLogoBytes(logoFile.getBytes());
            }

            // 3. Save the company
            String companyUrl = apiBaseUrl + "/auth/api/company";
            ResponseEntity<CompanyDTO> companyResponse = restTemplate.postForEntity(companyUrl, company,
                    CompanyDTO.class);

            if (companyResponse.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("responseMessage", "Company registered successfully and linked to employer.");
                return "login";
            } else {
                model.addAttribute("responseMessage",
                        "Company registration failed with status: " + companyResponse.getStatusCode());
                return "error-page";
            }

        } catch (IOException e) {
            model.addAttribute("responseMessage", "Error processing logo upload: " + e.getMessage());
            return "error-page";
        } catch (Exception e) {
            return "login";
        }
    }

}
