package com.jobportalspringmvc.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FrontendController {

    
        @GetMapping("/analytics")
    public String showanalytics(){
        return "admin";
    }

    @GetMapping("/profile")
    public String showProfile(){
        return "adminprofile";
    }

  @GetMapping("/query")
public String showQueryPage() {
    return "query";
}
   
}
