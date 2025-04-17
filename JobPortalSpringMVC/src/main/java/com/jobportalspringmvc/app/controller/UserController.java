package com.jobportalspringmvc.app.controller;

import com.jobportalspringmvc.app.dto.Role;
import com.jobportalspringmvc.app.dto.UserDTO;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.base.url}")
    private String baseUrl;

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/role")
    public String showRoleSelection() {
        return "role";
    }

    @GetMapping("/register")
    public String showRegisterForm(@RequestParam("role") String roleParam, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Role role = Role.valueOf(roleParam.toUpperCase());
            model.addAttribute("role", role.name());
            model.addAttribute("user", new UserDTO());
            return "register";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid role selected.");
            return "redirect:/";
        }
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            RedirectAttributes redirectAttributes, HttpSession session) {

        try {
            Role backendRole = Role.valueOf(role.toUpperCase());

            UserDTO userDto = UserDTO.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(password)
                    .role(backendRole)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDTO> request = new HttpEntity<>(userDto, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl + "/auth/register", request, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                redirectAttributes.addFlashAttribute("message", "Registration successful! Please login.");
                if(backendRole == Role.EMPLOYER) {
                    return "redirect:/company-form?email=" + email;
                }
                else if(backendRole == Role.JOBSEEKER) {
                    return "redirect:/jobseeker-register?email=" + email;
                }
                else {
                    redirectAttributes.addFlashAttribute("error", "Invalid role.");
                    return "redirect:/role";
                }
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Registration failed. Backend returned: " + response.getStatusCode());
                return "redirect:/register?role=" + role;
            }

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid role submitted.");
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something went wrong. Please try again.");
            return "redirect:/register?role=" + role;
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        String url = baseUrl + "/auth/login";

        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(loginData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();

                // Save token, role, and email in session
                session.setAttribute("token", responseBody.get("token"));
                session.setAttribute("role", responseBody.get("role"));
                session.setAttribute("email", responseBody.get("email"));

                String role = (String) responseBody.get("role");
                if ("EMPLOYER".equalsIgnoreCase(role)) {
                    return "redirect:/employer/home";
                } else if ("JOBSEEKER".equalsIgnoreCase(role)) {
                    return "redirect:/jobseeker/home";
                } else {
                    redirectAttributes.addFlashAttribute("error", "Unknown role.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Login failed. Invalid credentials,Please Enter a Valid Email and Password.");
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate(); // Clear all session data
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");
        return "redirect:/login";
    }
}