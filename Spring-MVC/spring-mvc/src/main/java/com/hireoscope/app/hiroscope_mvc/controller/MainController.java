package com.hireoscope.app.hiroscope_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    
    @GetMapping("/signup")
    public String homePage() {
        return "index";
    }
    
    @PostMapping("/signup")
    public String signup(@RequestParam String email, 
                         @RequestParam String password, 
                         Model model) {
        // You can process the signup data here
        model.addAttribute("message", "Signup successful for: " + email);
        return "success";
    }
}
