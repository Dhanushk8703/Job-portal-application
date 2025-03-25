package com.jobportal.app.spring_rest_api.controller;

import com.jobportal.app.spring_rest_api.model.User;
import com.jobportal.app.spring_rest_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    private final UserService userService;
    private final RestTemplate restTemplate;

    public OAuthController(UserService userService) {
        this.userService = userService;
        this.restTemplate = new RestTemplate();
    }

    // Fetch token from MVC (8080) and save in DB
    @PostMapping("/fetch-token")
    public ResponseEntity<?> fetchTokenFromMVC(@RequestParam String email) {
        String mvcUrl = "http://localhost:8080/auth/token";

        ResponseEntity<Map> response = restTemplate.getForEntity(mvcUrl, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            @SuppressWarnings("null")
            String accessToken = (String) response.getBody().get("access_token");

            // Save or update user OAuth token
            User updatedUser = userService.updateOAuthToken(email, accessToken);

            if (updatedUser != null) {
                return ResponseEntity.ok(Map.of("message", "OAuth token updated", "token", accessToken));
            }
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }
        return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch token"));
    }
}
