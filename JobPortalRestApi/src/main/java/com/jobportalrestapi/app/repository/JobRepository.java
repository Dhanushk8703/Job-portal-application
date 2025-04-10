package com.jobportalrestapi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportalrestapi.app.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByEmployerEmailAndJobStatus(String employerEmail, String string);
    
}

