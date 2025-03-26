package com.hireoscope.app.hiroscope_mvc.controller;

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
    private final String REST_API_URL = "http://localhost:8081/auth";

    @GetMapping("/") 
    public String showSignUpPage() {
        return "userrole";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";  // Thymeleaf template for login
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserDTO user, Model model) {
        String url = REST_API_URL + "/login";
        ResponseEntity<String> response = restTemplate.postForEntity(url, user, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            // Here you may store the token in a cookie/session as needed.
            model.addAttribute("message", "Login Successful! Token: " + response.getBody());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("message", "Invalid credentials");
            return "login";
        }
    }
}
