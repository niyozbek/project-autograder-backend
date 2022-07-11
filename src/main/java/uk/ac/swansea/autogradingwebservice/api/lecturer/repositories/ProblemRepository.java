package uk.ac.swansea.autogradingwebservice.api.lecturer.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;

public interface ProblemRepository extends CrudRepository<Problem, Long> {
}