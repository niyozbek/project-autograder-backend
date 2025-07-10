package uk.ac.swansea.autograder.general.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.ac.swansea.autograder.general.entities.Problem;
import uk.ac.swansea.autograder.general.entities.TestCase;
import uk.ac.swansea.autograder.api.student.controllers.dto.SubmissionDto;
import uk.ac.swansea.autograder.general.entities.Submission;
import uk.ac.swansea.autograder.general.entities.SubmissionTestResult;
import uk.ac.swansea.autograder.api.student.messaging.SubmissionSender;
import uk.ac.swansea.autograder.general.services.dto.ExecutionDto;
import uk.ac.swansea.autograder.general.services.dto.ExecutionResultDto;
import uk.ac.swansea.autograder.general.services.dto.RuntimeDto;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SubmissionMainService {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private ExecutionService executionService;
    @Autowired
    private SubmissionDetailService submissionDetailService;
    @Autowired
    private SubmissionTestResultService submissionTestResultService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private SubmissionSender submissionSender;

    public Submission submitSolution(Long id, SubmissionDto submissionDto, Long studentId)
            throws ResourceNotFoundException, BadRequestException {
        Problem problem = problemService.getProblem(id);
        if (!Objects.equals(problem.getStatus(), Problem.Status.ACTIVE)) {
            throw new BadRequestException();
        }

        Submission submission = submissionService.createSubmission(problem.getId(),
                studentId,
                submissionDto.getLanguage(),
                submissionDto.getVersion(),
                submissionDto.getFilename(),
                submissionDto.getCode());

        int totalTestCases = testCaseService.countAllTestCasesByProblemId(submission.getProblemId());
        submissionTestResultService.createSubmissionTestResult(submission.getId(), totalTestCases, 0, 0);
        submissionSender.send(submission.getId());
        return submission;
    }

    public void runSubmission(Long submissionId) throws ResourceNotFoundException {
        Submission submission = submissionService.getSubmission(submissionId);
        verifyCodeCompilation(submission);
        runTestCases(submission);
    }

    private void verifyCodeCompilation(Submission submission) throws ResourceNotFoundException {
        ExecutionDto executionDto = ExecutionDto.builder()
                .language(submission.getLanguage())
                .version(submission.getVersion())
                .filename(submission.getFilename())
                .code(submission.getCode())
                .build();
        TestCase firstTestCase = testCaseService.getFirstTestCaseByProblemId(submission.getProblemId());

        executionDto.setInput(firstTestCase.getInput());
        executionDto.setExpectedOutput(firstTestCase.getExpectedOutput());

        boolean isCompiled = executionService.isCompiled(executionDto);
        if (!isCompiled) {
            submission.setStatus(Submission.Status.COMPILE_ERROR);
            submissionService.updateSubmission(submission);
        }
    }

    private void runTestCases(Submission submission) throws ResourceNotFoundException {
        // don't run test cases if the code is not compiled
        if (submission.getStatus() == Submission.Status.COMPILE_ERROR) {
            return;
        }

        List<TestCase> testCases = testCaseService.getAllTestCasesByProblemId(submission.getProblemId());
        ExecutionDto executionDto = ExecutionDto.builder()
                .language(submission.getLanguage())
                .version(submission.getVersion())
                .filename(submission.getFilename())
                .code(submission.getCode())
                .build();
        SubmissionTestResult submissionTestResult = submissionTestResultService.getSubmissionTestResult(submission.getId());
        testCases.forEach(testCase -> {
            executionDto.setInput(testCase.getInput());
            executionDto.setExpectedOutput(testCase.getExpectedOutput());
            ExecutionResultDto executionResultDto = executionService.submit(executionDto);
            // save the result to submissionDetails
            submissionDetailService.createSubmissionDetail(submission.getId(),
                    testCase, executionResultDto.getOutput(), executionResultDto.getIsValid());
            submissionTestResult.setProcessedTestCases(submissionTestResult.getProcessedTestCases() + 1);
            if (executionResultDto.getIsValid()) {
                submissionTestResult.setCorrectTestCases(submissionTestResult.getCorrectTestCases() + 1);
            }
            submissionTestResultService.updateSubmissionTestResult(submissionTestResult);
        });
        submissionTestResult.setStatus(SubmissionTestResult.Status.COMPLETED);
        submissionTestResultService.updateSubmissionTestResult(submissionTestResult);

        if (Objects.equals(submissionTestResult.getProcessedTestCases(), submissionTestResult.getTotalTestCases())) {
            if (Objects.equals(submissionTestResult.getCorrectTestCases(), submissionTestResult.getTotalTestCases())) {
                submission.setStatus(Submission.Status.ACCEPTED);
            } else {
                submission.setStatus(Submission.Status.WRONG_ANSWER);
            }
            int grade = (int) Math.round(
                    (double) submissionTestResult.getCorrectTestCases() * 100 /
                            (double) submissionTestResult.getTotalTestCases());
            submission.setGrade(grade);
            submissionService.updateSubmission(submission);
        }

        // send it to another service
        log.info("sending to client >>");
        postToClient(submissionTestResult);
    }

    @Value("${app.client.submissionResultUrl}")
    private String clientUrl;

    private String postToClient(SubmissionTestResult submissionTestResult) {
        String uri = clientUrl;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(uri, submissionTestResult, String.class);
    }

    public List<RuntimeDto> getRuntime(Long id) {
        // maybe consider id later
        return executionService.getRuntimes();
    }
}
