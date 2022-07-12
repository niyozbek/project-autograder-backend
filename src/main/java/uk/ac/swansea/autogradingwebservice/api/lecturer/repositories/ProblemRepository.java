package uk.ac.swansea.autogradingwebservice.api.lecturer.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;

import java.util.List;

public interface ProblemRepository extends CrudRepository<Problem, Long> {
    List<Problem> findAllByLecturerId(Long id);
}