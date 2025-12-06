package uk.ac.swansea.autograder.api.controllers.dto;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Data
@Builder
public class NewUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String fullname;
    @NotBlank
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank
    private String password;
    private Set<RoleBriefDto> roles;
}
