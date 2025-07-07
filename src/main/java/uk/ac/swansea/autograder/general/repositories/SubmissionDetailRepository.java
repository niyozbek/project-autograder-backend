package uk.ac.swansea.autograder.general.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.swansea.autograder.general.entities.SubmissionDetail;

import java.util.List;

public interface SubmissionDetailRepository extends CrudRepository<SubmissionDetail, Long> {
    List<SubmissionDetail> findAllBySubmissionId(Long id);
}