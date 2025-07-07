package uk.ac.swansea.autograder.general.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autograder.general.entities.SubmissionTestResult;

public interface SubmissionTestResultRepository extends CrudRepository<SubmissionTestResult, Long> {
}