package uk.ac.swansea.autograder.auth.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autograder.auth.entities.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String lecturer);
}
