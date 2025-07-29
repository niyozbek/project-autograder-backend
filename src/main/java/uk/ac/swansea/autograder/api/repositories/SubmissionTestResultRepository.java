package uk.ac.swansea.autograder.api.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autograder.api.entities.SubmissionTestResult;

public interface SubmissionTestResultRepository extends CrudRepository<SubmissionTestResult, Long> {
}