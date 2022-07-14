package uk.ac.swansea.autogradingwebservice.api.student.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.TestCase;
import uk.ac.swansea.autogradingwebservice.api.lecturer.services.ProblemService;
import uk.ac.swansea.autogradingwebservice.api.lecturer.services.TestCaseService;
import uk.ac.swansea.autogradingwebservice.api.student.controllers.dto.SubmissionDto;
import uk.ac.swansea.autogradingwebservice.api.student.entities.Submission;
import uk.ac.swansea.autogradingwebservice.api.student.entities.SubmissionDetail;
import uk.ac.swansea.autogradingwebservice.api.student.messaging.SubmissionSender;
import uk.ac.swansea.autogradingwebservice.api.student.repositories.SubmissionDetailRepository;
import uk.ac.swansea.autogradingwebservice.api.student.repositories.SubmissionRepository;
import uk.ac.swansea.autogradingwebservice.api.student.services.dto.ExecutionDto;
import uk.ac.swansea.autogradingwebservice.api.student.services.dto.ExecutionResultDto;
import uk.ac.swansea.autogradingwebservice.api.student.services.dto.RuntimeDto;
import uk.ac.swansea.autogradingwebservice.exceptions.BadRequestException;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Objects;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private ExecutionService executionService;
    @Autowired
    private SubmissionDetailRepository submissionDetailRepository;
    @Autowired
    private SubmissionSender submissionSender;

    public Submission submitSolution(Long id, SubmissionDto submissionDto, Long studentId)
            throws ResourceNotFoundException, BadRequestException {
        Problem problem = problemService.getProblem(id);
        if (!Objects.equals(problem.getStatus(), Problem.Status.ACTIVE)) {
            throw new BadRequestException();
        }
        Submission submission = new Submission();
        submission.setProblemId(problem.getId());
        submission.setStudentId(studentId);
        submission.setLanguage(submissionDto.getLanguage());
        submission.setVersion(submissionDto.getVersion());
        submission.setFilename(submissionDto.getFileName());
        submission.setCode(submissionDto.getCode());
        submissionRepository.save(submission);
        submissionSender.send(submission.getId());
        return submission;
    }

    public void runSubmission(Long submissionId) throws ResourceNotFoundException {
        Submission submission = getSubmission(submissionId);
        runTestCases(submission);
    }

    private void runTestCases(Submission submission) {
        List<TestCase> testCases = testCaseService.getAllTestCasesByProblemId(submission.getProblemId());
        ExecutionDto executionDto = ExecutionDto.builder()
                .language(submission.getLanguage())
                .version(submission.getVersion())
                .fileName(submission.getFilename())
                .code(submission.getCode())
                .build();
        testCases.forEach(testCase -> {
            executionDto.setInput(testCase.getInput());
            executionDto.setExpectedOutput(testCase.getExpectedOutput());
            ExecutionResultDto executionResultDto = executionService.submit(executionDto);
            // save result to submissionDetails
            SubmissionDetail submissionDetail = new SubmissionDetail();
            submissionDetail.setSubmissionId(submission.getId());
            submissionDetail.setTestCase(testCase);
            submissionDetail.setActualOutput(executionResultDto.getOutput());
            submissionDetail.setTestCaseIsPassed(executionResultDto.getIsValid());
            submissionDetailRepository.save(submissionDetail);
        });
    }

    public List<Submission> getSubmissionsByProblemIdAndStudentId(Long problemId, Long studentId,
                                                                  Pageable pageable) {
        return submissionRepository.findAllByProblemIdAndStudentId(problemId, studentId, pageable);
    }

    // use problemId and studentID to verify if submissionId can be accessed
    public Submission getSubmission(Long submissionId, Long studentId) throws BadRequestException, ResourceNotFoundException {
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(
                ResourceNotFoundException::new);
        if (!Objects.equals(submission.getStudentId(), studentId)) {
            throw new BadRequestException();
        }
        return submission;
    }

    public Submission getSubmission(Long submissionId) throws ResourceNotFoundException {
        return submissionRepository.findById(submissionId).orElseThrow(
                ResourceNotFoundException::new);
    }

    public List<SubmissionDetail> getSubmissionDetail(Long submissionId, Long studentId) throws BadRequestException, ResourceNotFoundException {
        Submission submission = getSubmission(submissionId, studentId);
        return getSubmissionDetail(submission.getId());
    }

    public List<SubmissionDetail> getSubmissionDetail(Long submissionId) {
        return submissionDetailRepository
                .findAllBySubmissionId(submissionId);
    }

    public List<RuntimeDto> getRuntime(Long id) {
        // maybe consider id later
        return executionService.getRuntimes();
    }

    public List<Submission> getSubmissionsByStudentId(Long studentId, Pageable pageable) {
        return submissionRepository.findAllByStudentId(studentId, pageable);
    }

    public List<Submission> getSubmissionsByProblemId(Long problemId, Pageable pageable) {
        return submissionRepository.findAllByProblemId(problemId, pageable);
    }
}
