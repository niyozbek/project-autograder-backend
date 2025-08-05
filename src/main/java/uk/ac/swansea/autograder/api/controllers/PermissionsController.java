package uk.ac.swansea.autograder.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.swansea.autograder.api.controllers.dto.NewRoleDto;
import uk.ac.swansea.autograder.api.controllers.dto.PermissionDto;
import uk.ac.swansea.autograder.api.controllers.dto.RoleDto;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.Permission;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.services.PermissionService;
import uk.ac.swansea.autograder.general.services.RoleService;

import java.net.URI;
import java.util.List;

/**
 * Can create/update permissions.
 */
@RestController
@RequestMapping("api/permissions")
@Tag(name = "Manage permissions", description = "Can manage permissions")
public class PermissionsController {
    private final PermissionService permissionService;
    private final ModelMapper modelMapper;

    public PermissionsController(PermissionService permissionService, ModelMapper modelMapper) {
        this.permissionService = permissionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_PERMISSION')")
    @Operation(summary = "Get all permissions", description = "Returns a paginated list of permissions")
    public List<PermissionDto> getPermissions(@RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        List<Permission> permissions = permissionService.getPermissions(pageable);
        return modelMapper.map(permissions, new TypeToken<List<PermissionDto>>() {}.getType());
    }
}
