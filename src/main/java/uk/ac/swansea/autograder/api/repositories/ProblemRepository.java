package uk.ac.swansea.autograder.api.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.ac.swansea.autograder.api.entities.Problem;

import java.util.List;

public interface ProblemRepository extends PagingAndSortingRepository<Problem, Long>, CrudRepository<Problem, Long> {
    List<Problem> findAllByUserId(Long id, Pageable pageable);
}