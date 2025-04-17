package com.jobportalrestapi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportalrestapi.app.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByEmployerEmail(String email);

    Company findByEmployerEmail(String employerEmail);
}
