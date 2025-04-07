package com.hireoscope.app.hiroscope_mvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.hireoscope.app.hiroscope_mvc.model.CompanyDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class CompanyController {

 
    private final static String apiBaseUrl = "http://localhost:8081/";;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/company-form")
    public String showCompanyForm(Model model) {
        model.addAttribute("company", new CompanyDTO());
        return "company-form";
    }

    @PostMapping("/submit-company")
    public String submitCompany(@ModelAttribute CompanyDTO company, Model model) {
        String apiUrl = apiBaseUrl + "/api/companies";
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, company, String.class);
        
        model.addAttribute("responseMessage", response.getBody());
        return "success-page";
    }
}

