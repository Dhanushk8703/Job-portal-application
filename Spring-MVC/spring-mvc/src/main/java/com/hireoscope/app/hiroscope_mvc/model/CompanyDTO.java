package com.hireoscope.app.hiroscope_mvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    private Long companyId;
    private String companyName;
    private String companyType;
    private String industry;
    private String companySize;
    private String companyWebsite;
    private String companyDescription;
    private String companyPhone;
    private String linkedinProfile;
    private byte[] logo;
}
