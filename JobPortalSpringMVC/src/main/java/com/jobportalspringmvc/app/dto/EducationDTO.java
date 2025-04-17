package com.jobportalspringmvc.app.dto;


import java.time.LocalDate;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EducationDTO {
    private String degree;
    private String specialization;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String university;
    private String educationType;
    private String institution;
    private String rollNumber;
    private Double percentage;
    // private String marksheet;

}
