package com.jobportalrestapi.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportalrestapi.app.model.User;
import com.jobportalrestapi.app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmail(String employerEmail) {
        return userRepository.findByEmail(employerEmail);
    }
    
}
