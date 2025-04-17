package com.jobportalrestapi.app.services;

import org.springframework.stereotype.Service;

import com.jobportalrestapi.app.model.Company;
import com.jobportalrestapi.app.repository.CompanyRepository;
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    // ✅ Constructor injection to assign the repository
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

   
}

