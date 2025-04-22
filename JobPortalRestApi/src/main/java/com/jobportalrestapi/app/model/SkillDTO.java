package com.jobportalrestapi.app.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class SkillDTO {

    private String skillSet;
    private String proficiency;
    private String certificateName;

    private String certificateFile;
}
