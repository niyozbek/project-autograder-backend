package uk.ac.swansea.autogradingwebservice.api.lecturer.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.TestCase;

import java.util.List;

public interface TestCaseRepository extends PagingAndSortingRepository<TestCase, Long> {
    List<TestCase> findAllByProblemId(Long id);

    List<TestCase> findAllByProblemId(Long id, Pageable pageable);

    int countAllByProblemId(Long problemId);
}