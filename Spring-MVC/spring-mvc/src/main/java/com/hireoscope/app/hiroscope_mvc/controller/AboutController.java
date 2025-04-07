package com.hireoscope.app.hiroscope_mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("missionStatement", "At Hireoscope, we are dedicated to bridging the gap between ambitious professionals and top employers. Our mission is to provide a seamless, secure, and intelligent platform that empowers job seekers to find their dream careers and enables employers to discover the best talent. Launched in 2023, we’ve grown into a trusted name in the job market.");
        model.addAttribute("additionalInfo", "With cutting-edge technology like smart job matching and verified employer profiles, we ensure a fast and reliable experience for all users.");

        // Example team members
        List<Map<String, String>> teamMembers = new ArrayList<>();
        teamMembers.add(Map.of("name", "Jane Doe", "role", "CEO & Founder", "imageUrl", "https://via.placeholder.com/300x200?text=Jane+Doe"));
        teamMembers.add(Map.of("name", "John Smith", "role", "CTO", "imageUrl", "https://via.placeholder.com/300x200?text=John+Smith"));
        teamMembers.add(Map.of("name", "Emily Johnson", "role", "Head of HR", "imageUrl", "https://via.placeholder.com/300x200?text=Emily+Johnson"));
        model.addAttribute("teamMembers", teamMembers);

        // Example values
        List<Map<String, String>> values = new ArrayList<>();
        values.add(Map.of("title", "Community", "description", "We foster a supportive network for job seekers and employers to thrive together.", "icon", "fa-users"));
        values.add(Map.of("title", "Trust", "description", "We ensure a secure and transparent platform for all our users.", "icon", "fa-shield-alt"));
        values.add(Map.of("title", "Innovation", "description", "We leverage cutting-edge technology to redefine the job search experience.", "icon", "fa-lightbulb"));
        model.addAttribute("values", values);

        return "aboutus"; // Refers to this Thymeleaf template (about.html)
    }
}
