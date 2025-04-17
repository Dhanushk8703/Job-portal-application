package com.jobportalrestapi.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportalrestapi.app.model.Company;
import com.jobportalrestapi.app.model.User;
import com.jobportalrestapi.app.services.CompanyService;
import com.jobportalrestapi.app.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employer/api")
@RequiredArgsConstructor
public class EmployerController {

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('EMPLOYER')")
    @GetMapping("/company/{employerEmail}")
    public ResponseEntity<Company> getCompanyByEmail(@PathVariable String employerEmail) {
        return companyService.findByEmail(employerEmail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('EMPLOYER')")
    @GetMapping("/details/{employerEmail}")
    public ResponseEntity<User> getCompanyDetails(@PathVariable String employerEmail) {
        return userService.findByEmail(employerEmail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
