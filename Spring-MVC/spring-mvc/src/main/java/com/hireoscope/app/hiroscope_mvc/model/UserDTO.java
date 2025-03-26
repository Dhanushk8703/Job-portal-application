package com.hireoscope.app.hiroscope_mvc.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String country;
    private boolean emailUpdates;

    @Enumerated(EnumType.STRING)
    private Role role;
}
