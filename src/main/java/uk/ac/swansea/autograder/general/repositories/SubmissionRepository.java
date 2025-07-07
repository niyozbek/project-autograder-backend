package uk.ac.swansea.autograder.general.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.ac.swansea.autograder.general.entities.Submission;

import java.util.List;

public interface SubmissionRepository extends PagingAndSortingRepository<Submission, Long> {
    List<Submission> findAllByProblemIdAndStudentId(Long problemId, Long studentId, Pageable pageable);

    List<Submission> findAllByStudentId(Long studentId, Pageable pageable);

    List<Submission> findAllByProblemId(Long problemId, Pageable pageable);
}