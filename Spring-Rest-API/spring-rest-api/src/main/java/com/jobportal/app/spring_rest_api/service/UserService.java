package com.jobportal.app.spring_rest_api.service;

import com.jobportal.app.spring_rest_api.model.User;
import com.jobportal.app.spring_rest_api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Save user with encrypted password
    public User registerUser(String email, String password, String oauthToken) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Encrypt password
        user.setOauthToken(oauthToken);
        return userRepository.save(user);
    }

    // Update OAuth token for existing user
    public User updateOAuthToken(String email, String oauthToken) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setOauthToken(oauthToken);
            return userRepository.save(user);
        }
        return null;
    }
}

