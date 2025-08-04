package uk.ac.swansea.autograder.api.controllers.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RoleDto {
    private Integer id;
    private String name;
    private Set<PermissionDto> permissions;
}
