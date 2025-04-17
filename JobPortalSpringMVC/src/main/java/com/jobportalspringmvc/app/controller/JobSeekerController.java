package com.jobportalspringmvc.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class JobSeekerController {
    @GetMapping("/jobseeker/home")
    public String jobSeekerHome(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!"JOBSEEKER".equalsIgnoreCase((String) session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/login";
        }
        return "jobseeker-home";
    }

    @GetMapping("/jobListing")
    public String showJobListing() {
        return "joblisting";
    }

    @GetMapping("/companyList")
    public String showCompanyListing() {
        return "companylist";
    }

    @GetMapping("/signUp")
    public String showSignUpForm() {
        return "signInSignUp";
    }

    @GetMapping("/jobdesc")
    public String getJobDesc() {
        return "jobdesc";
    }

    @GetMapping("/companydesc")
    public String getCompanyDesc() {
        return "companydesc";
    }

    @GetMapping("/jobseeker-register")
    public String showJobSeekerRegisterForm() {
        return "jobseeker_register";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin";
    }

}
