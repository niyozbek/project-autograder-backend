package uk.ac.swansea.autograder.api.student.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autograder.api.student.entities.SubmissionTestResult;

public interface SubmissionTestResultRepository extends CrudRepository<SubmissionTestResult, Long> {
}