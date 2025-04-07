package com.hireoscope.app.hiroscope_mvc.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/custom-error")  // 🔥 Change from "/error" to "/custom-error"
    public String handleError(HttpServletRequest request, HttpServletResponse response) {
        return "Custom error handling";
    }

    public String getErrorPath() {
        return "/custom-error";
    }
}
