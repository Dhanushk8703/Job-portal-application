package com.hireoscope.app.hiroscope_mvc.controller;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.hireoscope.app.hiroscope_mvc.model.Role;
import com.hireoscope.app.hiroscope_mvc.model.UserDTO;

@Controller
@RequestMapping("/manual")
public class SignupController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String REST_API_URL = "http://localhost:8081/auth/register"; // Update with actual API URL

    @GetMapping("/signup")
    public String showSignupForm(@RequestParam(required = false) Role role, Model model) {
        UserDTO user = new UserDTO();
        user.setRole(role);
        model.addAttribute("user", user);
        return "signup"; 
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute UserDTO user, Model model) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<UserDTO> request = new HttpEntity<>(user, headers);
            ResponseEntity<String> response = restTemplate.exchange(REST_API_URL, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("message", "Account created successfully!");
                return "redirect:/dashboard";
            } else {
                model.addAttribute("message", "Signup failed. Try again!");
                return "signup";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Error registering user: " + e.getMessage());
            return "signup";
        }
    }
}
