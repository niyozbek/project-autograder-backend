package uk.ac.swansea.autograder.api.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.ac.swansea.autograder.api.controllers.dto.SubmissionDto;
import uk.ac.swansea.autograder.api.entities.Problem;
import uk.ac.swansea.autograder.api.entities.Submission;
import uk.ac.swansea.autograder.api.entities.TestCase;
import uk.ac.swansea.autograder.api.messaging.SubmissionSender;
import uk.ac.swansea.autograder.api.services.dto.ExecutionDto;
import uk.ac.swansea.autograder.api.services.dto.ExecutionResultDto;
import uk.ac.swansea.autograder.api.services.dto.RuntimeDto;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SubmissionMainService {
    private final ProblemService problemService;
    private final TestCaseService testCaseService;
    private final ExecutionService executionService;
    private final SubmissionDetailService submissionDetailService;
    private final SubmissionService submissionService;
    private final SubmissionSender submissionSender;

    public SubmissionMainService(ProblemService problemService,
                                 TestCaseService testCaseService,
                                 ExecutionService executionService,
                                 SubmissionDetailService submissionDetailService,
                                 SubmissionService submissionService,
                                 SubmissionSender submissionSender) {
        this.problemService = problemService;
        this.testCaseService = testCaseService;
        this.executionService = executionService;
        this.submissionDetailService = submissionDetailService;
        this.submissionService = submissionService;
        this.submissionSender = submissionSender;
    }

    public Submission submitSolution(SubmissionDto submissionDto)
            throws ResourceNotFoundException, BadRequestException {
        Problem problem = problemService.getProblem(submissionDto.getProblemId());
        if (!Objects.equals(problem.getStatus(), Problem.Status.ACTIVE)) {
            throw new BadRequestException();
        }

        Submission submission = submissionService.createSubmission(problem.getId(),
                submissionDto.getUserId(),
                submissionDto.getLanguage(),
                submissionDto.getVersion(),
                submissionDto.getFilename(),
                submissionDto.getCode());

        int totalTestCases = testCaseService.countAllTestCasesByProblemId(submission.getProblemId());
        submissionService.createSubmissionTestResult(submission.getId(), totalTestCases, 0, 0);
        submissionSender.send(submission.getId());
        return submission;
    }

    public void runSubmission(Long submissionId) throws ResourceNotFoundException {
        Submission submission = submissionService.getSubmission(submissionId);

        List<TestCase> testCases = testCaseService.getAllTestCasesByProblemId(submission.getProblemId());
        ExecutionDto executionDto = ExecutionDto.builder()
                .language(submission.getLanguage())
                .version(submission.getVersion())
                .filename(submission.getFilename())
                .code(submission.getCode())
                .build();
        for (TestCase testCase : testCases) {
            executionDto.setInput(testCase.getInput());
            executionDto.setExpectedOutput(testCase.getExpectedOutput());
            ExecutionResultDto executionResultDto;
            try {
                executionResultDto = executionService.submit(executionDto);
            } catch (Exception e) {
                submission.setStatus(Submission.Status.COMPILE_ERROR);
                submission.setOutput(e.getMessage());
                break; // the loop
            }
            // save the result to submissionDetails
            submissionDetailService.createSubmissionDetail(submission.getId(),
                    testCase, executionResultDto.getOutput(), executionResultDto.getIsValid());
            submission.setProcessedTestCases(submission.getProcessedTestCases() + 1);
            if (executionResultDto.getIsValid()) {
                submission.setCorrectTestCases(submission.getCorrectTestCases() + 1);
            }
        }

        if (Objects.equals(submission.getCorrectTestCases(), submission.getTotalTestCases())) {
            submission.setStatus(Submission.Status.ACCEPTED);
        } else {
            submission.setStatus(Submission.Status.WRONG_ANSWER);
        }
        int grade = (int) Math.round(
                (double) submission.getCorrectTestCases() * 100 /
                        (double) submission.getTotalTestCases());
        submission.setGrade(grade);

        submissionService.updateSubmission(submission);

        // send it to another service
        if (clientEnabled) {
            log.info("sending to client >>");
            postToClient(submission);
        }
    }

    @Value("${app.client.submissionResultUrl}")
    private String clientUrl;

    @Value("${app.client.enabled}")
    private Boolean clientEnabled;

    private void postToClient(Submission submissionTestResult) {
        String uri = clientUrl;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, submissionTestResult, String.class);
    }

    public List<RuntimeDto> getRuntimes() {
        return executionService.getRuntimes();
    }
}
