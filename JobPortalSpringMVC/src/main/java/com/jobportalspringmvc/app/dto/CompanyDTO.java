package com.jobportalspringmvc.app.dto;

import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    private Long id;
    private String employerEmail;
    private String companyName;
    private String companyType;
    private String industry;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private String companySize;
    private String companyWebsite;
    private String companyDescription;
    private String companyPhone;
    private String linkedinProfile;

    @JsonIgnore
    private MultipartFile logo;  // This will not be serialized to JSON

    private byte[] logoBytes;

    private String status; // Added status field to track approval status (e.g., "Pending", "Approved", "Rejected")
}
