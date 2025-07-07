package uk.ac.swansea.autograder.auth.controllers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
