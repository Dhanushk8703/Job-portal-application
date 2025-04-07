package com.jobportal.app.spring_rest_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobportal.app.spring_rest_api.model.Company;
import com.jobportal.app.spring_rest_api.repository.CompanyRepository;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyRestController {

    private final CompanyRepository companyRepository;

    public CompanyRestController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @PostMapping
    public ResponseEntity<String> saveCompany(@RequestBody Company company) {
        companyRepository.save(company);
        return ResponseEntity.ok("Company saved successfully");
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyRepository.findAll());
    }
}

