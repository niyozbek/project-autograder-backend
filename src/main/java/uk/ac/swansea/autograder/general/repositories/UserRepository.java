package uk.ac.swansea.autograder.general.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import uk.ac.swansea.autograder.general.entities.User;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u LEFT JOIN u.roles role WHERE role.id = ?1")
    List<User> findAllUsersByRoleId(Integer role, Pageable pageable);

    User findByEmail(String email);
}