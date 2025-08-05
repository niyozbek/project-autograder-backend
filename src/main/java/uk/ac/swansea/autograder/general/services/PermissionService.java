package uk.ac.swansea.autograder.general.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.Permission;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.repositories.PermissionRepository;

import java.util.List;

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

    public List<Permission> getPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable).toList();
    }
}
