package uk.ac.swansea.autograder.general.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.ac.swansea.autograder.general.entities.Permission;

import java.util.Optional;


public interface PermissionRepository extends CrudRepository<Permission, Integer>, PagingAndSortingRepository<Permission, Integer> {
    Optional<Permission> findByName(String name);
}
