package com.jobportalspringmvc.app.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class JobSeekerDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dob;
    
    private List<EducationDTO> educationList = new ArrayList<>();
    private List<WorkExperienceDTO> workExperienceList =  new ArrayList<>();
    private List<SkillDTO> skillList = new ArrayList<>();
}