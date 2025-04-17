package com.jobportalrestapi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportalrestapi.app.model.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {}