package uk.ac.swansea.autogradingwebservice.api.student.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autogradingwebservice.api.student.entities.Submission;

import java.util.List;

public interface SubmissionRepository extends CrudRepository<Submission, Long> {
    List<Submission> findAllByProblemIdAndStudentId(Long problemId, Long studentId);

    List<Submission> findAllByStudentId(Long studentId);
}