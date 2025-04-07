package com.jobportal.app.spring_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.app.spring_rest_api.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
