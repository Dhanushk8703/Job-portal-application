package com.jobportalrestapi.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dob;
    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List<Education> educationList = new ArrayList<>();

    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List<Skill> skills = new ArrayList<>();
}
