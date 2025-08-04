package uk.ac.swansea.autograder.general.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.controllers.dto.NewRoleDto;
import uk.ac.swansea.autograder.api.controllers.dto.PermissionDto;
import uk.ac.swansea.autograder.api.controllers.dto.RoleDto;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.Permission;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionService permissionService;

    public RoleService(RoleRepository roleRepository, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    public Role getRoleByName(String name) throws ResourceNotFoundException {
        return roleRepository.findByName(name)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<Role> getRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).toList();
    }

    public Role createRole(NewRoleDto newRoleDto) throws ResourceNotFoundException {
        Role role = new Role();
        role.setName(newRoleDto.getName());
        for (PermissionDto permissionDto : newRoleDto.getPermissions()) {
            Permission permission = permissionService.getPermissionById(permissionDto.getId());
            role.getPermissions().add(permission);
        }
        return roleRepository.save(role);
    }

    public Role getRole(Integer id) throws ResourceNotFoundException {
        return roleRepository.findById(id).orElseThrow();
    }

    public Role updateRole(RoleDto roleDto) throws ResourceNotFoundException {
        Role role = getRole(roleDto.getId());
        role.setName(roleDto.getName());
        // TODO: update permissions
        return roleRepository.save(role);
    }
}
