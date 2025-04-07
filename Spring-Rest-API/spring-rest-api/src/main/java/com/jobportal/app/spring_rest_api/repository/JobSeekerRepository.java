package com.jobportal.app.spring_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.app.spring_rest_api.model.JobSeeker;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, String> {
}

