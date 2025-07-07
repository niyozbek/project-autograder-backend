package uk.ac.swansea.autograder.api.student.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.student.entities.Submission;
import uk.ac.swansea.autograder.api.student.entities.SubmissionTestResult;
import uk.ac.swansea.autograder.api.student.repositories.SubmissionTestResultRepository;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

@Service
public class SubmissionTestResultService {
    @Autowired
    private SubmissionTestResultRepository submissionTestResultRepository;
    @Autowired
    private SubmissionService submissionService;

    public void createSubmissionTestResult(Long submissionId, int totalTestCases, int processedTestCases, int correctTestCases) {
        SubmissionTestResult submissionTestResult = new SubmissionTestResult();
        submissionTestResult.setStatus(SubmissionTestResult.Status.PROCESSING);
        submissionTestResult.setSubmissionId(submissionId);
        submissionTestResult.setTotalTestCases(totalTestCases);
        submissionTestResult.setProcessedTestCases(processedTestCases);
        submissionTestResult.setCorrectTestCases(correctTestCases);

        submissionTestResultRepository.save(submissionTestResult);
    }


    public SubmissionTestResult getSubmissionTestResult(Long submissionId)
            throws ResourceNotFoundException {
        return submissionTestResultRepository.findById(submissionId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public SubmissionTestResult getSubmissionTestResult(Long submissionId, Long studentId)
            throws BadRequestException, ResourceNotFoundException {
        Submission submission = submissionService.getSubmission(submissionId, studentId);
        return getSubmissionTestResult(submission.getId());
    }

    public void updateSubmissionTestResult(SubmissionTestResult submissionTestResult) {
        submissionTestResultRepository.save(submissionTestResult);
    }
}
