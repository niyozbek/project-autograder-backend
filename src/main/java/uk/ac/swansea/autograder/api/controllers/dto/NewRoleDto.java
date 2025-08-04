package uk.ac.swansea.autograder.api.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class NewRoleDto {
    @NotBlank
    private String name;
    private Set<PermissionDto> permissions;
}
