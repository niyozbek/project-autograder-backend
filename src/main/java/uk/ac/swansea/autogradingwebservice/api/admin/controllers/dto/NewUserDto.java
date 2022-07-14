package uk.ac.swansea.autogradingwebservice.api.admin.controllers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
