package uk.ac.swansea.autograder.api.services;

import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.repositories.RoleRepository;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getAdminRole() throws ResourceNotFoundException {
        return roleRepository.findByName(Role.RoleType.ADMIN)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Role getLecturerRole() throws ResourceNotFoundException {
        return roleRepository.findByName(Role.RoleType.LECTURER)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Role getStudentRole() throws ResourceNotFoundException {
        return roleRepository.findByName(Role.RoleType.STUDENT)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
