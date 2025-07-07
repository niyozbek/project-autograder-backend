package uk.ac.swansea.autograder.general.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.general.entities.TestCase;
import uk.ac.swansea.autograder.general.entities.Submission;
import uk.ac.swansea.autograder.general.entities.SubmissionDetail;
import uk.ac.swansea.autograder.general.repositories.SubmissionDetailRepository;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class SubmissionDetailService {
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private SubmissionDetailRepository submissionDetailRepository;

    public List<SubmissionDetail> getSubmissionDetail(Long submissionId, Long studentId) throws BadRequestException, ResourceNotFoundException {
        Submission submission = submissionService.getSubmission(submissionId, studentId);
        return getSubmissionDetail(submission.getId());
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
