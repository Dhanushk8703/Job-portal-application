package com.hireoscope.app.hiroscope_mvc.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.hireoscope.app.hiroscope_mvc.model.UserDTO;

@Controller
public class UserController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String LOGIN_API_URL = "http://localhost:8081/auth/login"; 
    private final String EMPLOYER_FORM_API_URL = "http://localhost:8080/employer/form"; 

    @GetMapping("/")
    public String showSignUpPage() {
        return "userrole";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

    @PostMapping("/perform_login")
    public String loginUser(@ModelAttribute UserDTO user, Model model, HttpServletResponse response) {
        try {
            ResponseEntity<String> apiResponse = restTemplate.postForEntity(LOGIN_API_URL, user, String.class);

            if (apiResponse.getStatusCode().is2xxSuccessful()) {
                String token = apiResponse.getBody().trim();
                token = URLEncoder.encode(token, StandardCharsets.UTF_8);

                Cookie cookie = new Cookie("JWT_TOKEN", token);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(60 * 60 * 24); // 1-day expiry
                response.addCookie(cookie);

                return "redirect:/EmployeerForm";
            } else {
                model.addAttribute("message", "Invalid credentials, try again.");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Authentication failed: " + e.getMessage());
            return "login";
        }
    }

    @GetMapping("/EmployeerForm")
    public String showEmployerForm(HttpServletRequest request, Model model) {
        String jwtToken = getJwtFromCookies(request);

        if (jwtToken == null) {
            model.addAttribute("message", "Unauthorized access. Please log in.");
            return "login";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> apiResponse = restTemplate.exchange(
                    EMPLOYER_FORM_API_URL,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (apiResponse.getStatusCode().is2xxSuccessful()) {
                return "employerForm";
            } else {
                model.addAttribute("message", "Access Denied.");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
            return "login";
        }
    }

    private String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> "JWT_TOKEN".equals(cookie.getName()))
                    .findFirst();
            return jwtCookie.map(Cookie::getValue).orElse(null);
        }
        return null;
    }
}
