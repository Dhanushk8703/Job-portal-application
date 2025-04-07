package com.jobportal.app.spring_rest_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "industry")
    private String industry;

    @Column(name = "company_size")
    private String companySize;

    @Column(name = "company_website")
    private String companyWebsite;

    @Column(name = "company_description", columnDefinition = "TEXT")
    private String companyDescription;

    @Column(name = "company_phone")
    private String companyPhone;

    

    @Column(name = "linkedin_profile")
    private String linkedinProfile;

    @Lob
    @Column(name = "logo")
    private byte[] logo;
}

