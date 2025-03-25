package com.hireoscope.app.hiroscope_mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/user")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal OidcUser user) {
        Map<String, Object> response = new HashMap<>();
        response.put("name", user.getFullName());
        response.put("email", user.getEmail());
        response.put("roles", List.of("ROLE_USER"));  // Assign roles dynamically
        response.put("token", user.getIdToken().getTokenValue()); // ✅ Use Google ID Token

        return response;
    }
}
