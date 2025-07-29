package uk.ac.swansea.autograder.api.services;

import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.entities.TestCase;
import uk.ac.swansea.autograder.api.entities.SubmissionDetail;
import uk.ac.swansea.autograder.api.repositories.SubmissionDetailRepository;

import java.util.List;

@Service
public class SubmissionDetailService {
    private final SubmissionDetailRepository submissionDetailRepository;

    public SubmissionDetailService(SubmissionDetailRepository submissionDetailRepository) {
        this.submissionDetailRepository = submissionDetailRepository;
    }

    public List<SubmissionDetail> getSubmissionDetail(Long submissionId) {
        return submissionDetailRepository
                .findAllBySubmissionId(submissionId);
    }

    public void createSubmissionDetail(Long submissionId, TestCase testCase, String output, Boolean isValid) {
        SubmissionDetail submissionDetail = new SubmissionDetail();
        submissionDetail.setSubmissionId(submissionId);
        submissionDetail.setTestCase(testCase);
        submissionDetail.setActualOutput(output);
        submissionDetail.setTestCaseIsPassed(isValid);
        submissionDetailRepository.save(submissionDetail);
    }
}
