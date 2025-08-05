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
import uk.ac.swansea.autograder.api.controllers.dto.RoleDto;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.services.RoleService;

import java.net.URI;
import java.util.List;

/**
 * Can create/update roles.
 */
@RestController
@RequestMapping("api/roles")
@Tag(name = "Manage roles", description = "Can manage roles")
public class RolesController {
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    public RolesController(RoleService roleService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    @Operation(summary = "Get all roles", description = "Returns a paginated list of roles with permissions")
    public List<RoleDto> getUsers(@RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        List<Role> roles = roleService.getRoles(pageable);
        return modelMapper.map(roles, new TypeToken<List<RoleDto>>() {}.getType());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @Operation(summary = "Create new role", description = "Creates a new role, with permissions")
    public ResponseEntity<RoleDto> createUser(@Valid @RequestBody NewRoleDto newRoleDto) throws ResourceNotFoundException {
        Role role = roleService.createRole(newRoleDto);
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId())
                .toUri();
        return ResponseEntity.created(location).body(roleDto);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    @Operation(summary = "Get role", description = "Returns a role with permissions")
    public ResponseEntity<RoleDto> getUser(@PathVariable Integer id) throws ResourceNotFoundException {
        Role role = roleService.getRole(id);
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return ResponseEntity.ok(roleDto);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    @Operation(summary = "Update role", description = "Updates role, with permissions")
    public RoleDto updateUser(@Valid @RequestBody RoleDto roleDto) throws ResourceNotFoundException {
        Role role = roleService.updateRole(roleDto);
        return modelMapper.map(role, RoleDto.class);
    }
}
