package uk.ac.swansea.autograder.general.services;

import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.repositories.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) throws ResourceNotFoundException {
        return roleRepository.findByName(name)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Role getRoleById(Integer id) throws ResourceNotFoundException {
        return roleRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
