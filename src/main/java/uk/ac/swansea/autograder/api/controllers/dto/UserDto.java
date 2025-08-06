package uk.ac.swansea.autograder.api.controllers.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String fullname;
    private Set<RoleBriefDto> roles;
}
