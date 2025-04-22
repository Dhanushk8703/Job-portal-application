package com.jobportalrestapi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobportalrestapi.app.model.WorkExperience;
public interface ExperienceRepository extends JpaRepository<WorkExperience, Long> {}