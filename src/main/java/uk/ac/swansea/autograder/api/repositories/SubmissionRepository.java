package uk.ac.swansea.autograder.api.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.ac.swansea.autograder.api.entities.Submission;

import java.util.List;

public interface SubmissionRepository extends PagingAndSortingRepository<Submission, Long>, CrudRepository<Submission, Long> {
    List<Submission> findAllByProblemIdAndUserId(Long problemId, Long userId, Pageable pageable);

    List<Submission> findAllByuserId(Long userId, Pageable pageable);

    List<Submission> findAllByProblemId(Long problemId, Pageable pageable);
}