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

    public Role getLecturerRole() throws ResourceNotFoundException {
        return roleRepository.findByName("LECTURER")
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Role getStudentRole() throws ResourceNotFoundException {
        return roleRepository.findByName("STUDENT")
                .orElseThrow(ResourceNotFoundException::new);
    }
}
