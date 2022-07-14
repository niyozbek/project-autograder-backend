package uk.ac.swansea.autogradingwebservice.auth.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import uk.ac.swansea.autogradingwebservice.auth.entities.User;

import java.util.List;


public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u LEFT JOIN u.roles role WHERE role.id = ?1")
    List<User> findAllUsersByRoleId(Integer role, Pageable pageable);
}