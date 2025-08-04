package uk.ac.swansea.autograder.general.services;

import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.Permission;
import uk.ac.swansea.autograder.general.repositories.PermissionRepository;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission getPermissionById(Integer id) throws ResourceNotFoundException {
        return permissionRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
