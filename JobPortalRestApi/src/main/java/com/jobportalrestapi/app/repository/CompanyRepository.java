package com.jobportalrestapi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportalrestapi.app.model.Company;
import com.jobportalrestapi.app.model.CompanyStatus;

public interface CompanyRepository extends JpaRepository<Company, Long> {
List<Company> findByStatus(CompanyStatus status);

    Company findByEmployerEmail(String employerEmail);
}
