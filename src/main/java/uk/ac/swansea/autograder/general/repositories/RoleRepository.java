package uk.ac.swansea.autograder.general.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autograder.general.entities.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String lecturer);
}
