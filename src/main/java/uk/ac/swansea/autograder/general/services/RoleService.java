package uk.ac.swansea.autograder.general.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.repositories.RoleRepository;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

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
