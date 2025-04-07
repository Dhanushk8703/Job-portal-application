package com.jobportal.app.spring_rest_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employer {

    @Id
    @Column(name = "employer_id")
    private String employerId;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;
}
