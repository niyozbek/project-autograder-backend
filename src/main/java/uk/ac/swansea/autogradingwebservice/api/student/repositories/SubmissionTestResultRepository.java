package uk.ac.swansea.autogradingwebservice.api.student.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autogradingwebservice.api.student.entities.SubmissionTestResult;

public interface SubmissionTestResultRepository extends CrudRepository<SubmissionTestResult, Long> {
}