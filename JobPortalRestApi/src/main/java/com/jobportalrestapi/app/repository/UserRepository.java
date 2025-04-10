package com.jobportalrestapi.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportalrestapi.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer > {
   
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email); 
}

