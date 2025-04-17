package com.jobportalrestapi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobportalrestapi.app.model.JobSeeker;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {}