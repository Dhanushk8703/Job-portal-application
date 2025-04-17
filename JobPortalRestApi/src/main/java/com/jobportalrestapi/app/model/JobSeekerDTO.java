package com.jobportalrestapi.app.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class JobSeekerDTO {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dob;
    private List<EducationDTO> educationList;
    private List<WorkExperienceDTO> workExperienceList;
    private List<SkillDTO> skillList;
}
