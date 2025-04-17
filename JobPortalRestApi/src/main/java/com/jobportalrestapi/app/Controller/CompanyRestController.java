package com.jobportalrestapi.app.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobportalrestapi.app.model.Company;
import com.jobportalrestapi.app.repository.CompanyRepository;
import com.jobportalrestapi.app.services.CompanyService;

import java.util.List;
import java.util.Map;

@RestController
public class CompanyRestController {

    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    public CompanyRestController(
            CompanyRepository companyRepository,
            CompanyService companyService
    ) {
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    @PostMapping("/auth/api/company")
    public ResponseEntity<String> saveCompany(@RequestBody Company company) {
        companyRepository.save(company);
        return ResponseEntity.ok("Company saved successfully");
    }

    @GetMapping("/api/company/{employerEmail}")
    public ResponseEntity<Company> getCompanyByEmail(@PathVariable String employerEmail) {
        return companyService.findByEmail(employerEmail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/auth/api/company")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyRepository.findAll());
    }
}
