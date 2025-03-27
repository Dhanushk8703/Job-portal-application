package com.hireoscope.app.hiroscope_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EmployerController {
  @GetMapping("/employer/form")
    public String employer() {
        return "EmployeerForm";
    }
    
    @GetMapping("/userRole")
    public String userRole() {
        return "UserRole";
    }

    @GetMapping("EMPLOYER/login")
    public String login() {
        return "login";
    }
    
}