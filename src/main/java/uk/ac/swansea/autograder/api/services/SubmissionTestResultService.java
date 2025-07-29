package uk.ac.swansea.autograder.api.services;

import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.entities.SubmissionTestResult;
import uk.ac.swansea.autograder.api.repositories.SubmissionTestResultRepository;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

@Service
public class SubmissionTestResultService {
    private final SubmissionTestResultRepository submissionTestResultRepository;

    public SubmissionTestResultService(SubmissionTestResultRepository submissionTestResultRepository) {
        this.submissionTestResultRepository = submissionTestResultRepository;
    }

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

    public void updateSubmissionTestResult(SubmissionTestResult submissionTestResult) {
        submissionTestResultRepository.save(submissionTestResult);
    }
}
