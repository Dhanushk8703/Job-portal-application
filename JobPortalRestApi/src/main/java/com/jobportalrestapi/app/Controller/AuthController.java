package com.jobportalrestapi.app.Controller;

import com.jobportalrestapi.app.DTO.JwtResponse;
import com.jobportalrestapi.app.DTO.LoginRequest;
import com.jobportalrestapi.app.Security.JwtUtil;
import com.jobportalrestapi.app.model.User;
import com.jobportalrestapi.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        // Encode password before saving
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Fetch user role (or any extra data) from DB
            User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

            return ResponseEntity.ok(
                new JwtResponse(token, user.getEmail(), user.getRole().name())
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
