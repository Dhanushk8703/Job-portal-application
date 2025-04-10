package com.jobportalrestapi.app.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobportalrestapi.app.model.Company;
import com.jobportalrestapi.app.repository.CompanyRepository;
import com.jobportalrestapi.app.services.CompanyService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
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

    @PostMapping("/api/company")
    public ResponseEntity<String> saveCompany(@RequestBody Company company) {
        companyRepository.save(company);
        return ResponseEntity.ok("Company saved successfully");
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyRepository.findAll());
    }
}
