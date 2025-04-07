package com.jobportal.app.spring_rest_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.app.spring_rest_api.model.Role;
import com.jobportal.app.spring_rest_api.model.User;
import com.jobportal.app.spring_rest_api.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public boolean validateUser(String email, String password, Role role) {
        // Fetch user by email
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            // Compare the provided password with the stored password
            return user.get().getPassword().equals(password) && user.get().getRole().equals(role);
        }
        return false;
    }
    
}
