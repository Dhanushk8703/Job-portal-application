package com.jobportalspringmvc.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String oauthToken;
    private Role role;
}