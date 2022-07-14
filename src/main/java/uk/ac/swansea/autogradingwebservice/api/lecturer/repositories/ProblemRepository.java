package uk.ac.swansea.autogradingwebservice.api.lecturer.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;

import java.util.List;

public interface ProblemRepository extends PagingAndSortingRepository<Problem, Long> {
    List<Problem> findAllByLecturerId(Long id, Pageable pageable);
}