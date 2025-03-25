package com.hireoscope.app.hiroscope_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}