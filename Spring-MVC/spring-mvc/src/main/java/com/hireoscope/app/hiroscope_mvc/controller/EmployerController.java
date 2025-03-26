package com.hireoscope.app.hiroscope_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EmployerController {
  @GetMapping("/employer")
    public String employer() {
        return "EmployeerForm";
    }
    
    @GetMapping("/userrole")
    public String userrole() {
        return "Userrole";
    }

    @GetMapping("/login-page")
    public String login() {
        return "login";
    }
    
}