package com.jobportal.app.spring_rest_api.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @GetMapping("/profile")
    public Map<String, Object> getUserProfile(@AuthenticationPrincipal OAuth2User user) {
        if (user != null) {
            return Map.of(
                "name", user.getAttribute("name"),
                "email", user.getAttribute("email"),
                "picture", user.getAttribute("picture")
            );
        }
        return Map.of("error", "User not authenticated");
    }

    @GetMapping("/user")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "username", jwt.getClaim("email"),
                "roles", jwt.getClaim("roles")
        );
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Hello Admin! You have access.";
    }
}