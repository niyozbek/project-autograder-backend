package uk.ac.swansea.autogradingwebservice.api.lecturer.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.TestCase;

import java.util.List;

public interface TestCaseRepository extends CrudRepository<TestCase, Long> {
    List<TestCase> findAllByProblemId(Long id);
}