package uk.ac.swansea.autograder.general.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.ac.swansea.autograder.general.entities.TestCase;

import java.util.List;
import java.util.Optional;

public interface TestCaseRepository extends PagingAndSortingRepository<TestCase, Long>, CrudRepository<TestCase, Long> {
    List<TestCase> findAllByProblemId(Long id);

    List<TestCase> findAllByProblemId(Long id, Pageable pageable);

    int countAllByProblemId(Long problemId);

    Optional<TestCase> findFirstByProblemId(Long problemId);
}