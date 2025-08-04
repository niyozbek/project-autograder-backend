package uk.ac.swansea.autograder.general.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.ac.swansea.autograder.general.entities.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer>, PagingAndSortingRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
