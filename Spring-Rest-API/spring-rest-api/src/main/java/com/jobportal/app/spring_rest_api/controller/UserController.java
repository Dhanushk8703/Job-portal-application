package com.jobportal.app.spring_rest_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.app.spring_rest_api.model.User;
import com.jobportal.app.spring_rest_api.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public Map<String, String> registerUser(@RequestBody User user) {
        userService.addUser(user);
        return Map.of("message", "User registered successfully");
    }

    @PostMapping("/auth/login")
public Map<String, String> loginUser(@RequestBody User user) {
    boolean isValidUser = userService.validateUser(user.getEmail(), user.getPassword(), user.getRole());
    if (isValidUser) {
        return Map.of("message", "Login successful");
    } else {
        return Map.of("message", "Invalid email or password");
    }
}

    @GetMapping("/admin")
    public String adminAccess() {
        return "Hello Admin! You have updated access.";
    }
}