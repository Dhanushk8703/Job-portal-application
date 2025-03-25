package com.hireoscope.app.hiroscope_mvc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public Map<String, Object> getUserProfile(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) {
            return Map.of("error", "User is not authenticated");
        }

        return Map.of(
            "name", oidcUser.getFullName(),
            "email", oidcUser.getEmail(),
            "picture", oidcUser.getPicture(),
            "token", oidcUser.getIdToken().getTokenValue()
        );
    }
}
