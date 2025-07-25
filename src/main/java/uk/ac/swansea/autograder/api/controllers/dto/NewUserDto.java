package uk.ac.swansea.autograder.api.controllers.dto;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
public class NewUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
