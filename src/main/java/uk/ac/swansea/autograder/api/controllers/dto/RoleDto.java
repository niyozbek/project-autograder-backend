package uk.ac.swansea.autograder.api.controllers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDto extends RoleBriefDto{
    private Set<PermissionDto> permissions;
}
