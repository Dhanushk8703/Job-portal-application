package com.jobportalrestapi.app.Controller;

import com.jobportalrestapi.app.model.Company;
import com.jobportalrestapi.app.model.CompanyStatus;
import com.jobportalrestapi.app.repository.CompanyRepository;
import com.jobportalrestapi.app.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        company.setStatus(CompanyStatus.PENDING); // Ensure it's pending initially
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

    // ✅ Get All Pending Companies (for Admin)
    @GetMapping("/admin/companies/pending")
    public ResponseEntity<List<Company>> getPendingCompanies() {
        return ResponseEntity.ok(companyRepository.findByStatus(CompanyStatus.PENDING));
    }

    @PostMapping("/admin/company/{id}/approve")
    public ResponseEntity<String> approveCompany(@PathVariable Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.setStatus(CompanyStatus.APPROVED);
        companyRepository.save(company);
        return ResponseEntity.ok("Company approved successfully");
    }
    
    @PostMapping("/admin/company/{id}/reject")
    public ResponseEntity<String> rejectCompany(@PathVariable Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.setStatus(CompanyStatus.REJECTED);
        companyRepository.save(company);
        return ResponseEntity.ok("Company rejected successfully");
    }
    
}
