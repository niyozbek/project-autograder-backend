package uk.ac.swansea.autograder.api.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.controllers.dto.SubmissionBriefDto;
import uk.ac.swansea.autograder.api.controllers.dto.SubmissionDetailDto;
import uk.ac.swansea.autograder.api.controllers.dto.SubmissionDto;
import uk.ac.swansea.autograder.api.controllers.dto.UserDto;
import uk.ac.swansea.autograder.config.MyUserDetails;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.UnauthorizedException;
import uk.ac.swansea.autograder.general.entities.Problem;
import uk.ac.swansea.autograder.general.entities.Submission;
import uk.ac.swansea.autograder.general.entities.SubmissionTestResult;
import uk.ac.swansea.autograder.general.services.SubmissionDetailService;
import uk.ac.swansea.autograder.general.services.SubmissionService;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.services.SubmissionTestResultService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Get all submissions+
 * Get a specific submission+
 * View every test case of every submission+
 */
@RestController
@RequestMapping("api/submissions")
public class SubmissionsController {
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private SubmissionDetailService submissionDetailService;
    @Autowired
    private SubmissionTestResultService submissionTestResultService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get the list of submitted solutions by the student
     * also for a specific problem
     *
     * @return list of submissions
     */
    @GetMapping("own")

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER', 'STUDENT')")
    public List<SubmissionBriefDto> getOwnSubmissions(Authentication authentication,
                                                   @RequestParam(required = false) Long problemId,
                                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        List<Submission> submissions;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        if (problemId != null) {
            submissions = submissionService
                    .getSubmissionsByProblemIdAndStudentId(problemId, user.getId(), pageable);
        } else {
            submissions = submissionService
                    .getSubmissionsByStudentId(user.getId(), pageable);
        }
        return modelMapper.map(submissions, new TypeToken<List<SubmissionBriefDto>>() {}.getType());
    }

    /**
     * Get the specific submission
     *
     * @return submission
     */
    @GetMapping("{submissionId}")
    @PreAuthorize("hasAuthority('LECTURER')")
    public SubmissionDto getSubmission(@PathVariable Long submissionId)
            throws ResourceNotFoundException {
        Submission submission = submissionService.getSubmission(submissionId);
        return modelMapper.map(submission, SubmissionDto.class);
    }

    /**
     * Get own submission
     *
     * @return submission
     */
    @GetMapping("own/{submissionId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER', 'STUDENT')")
    public SubmissionDto getOwnSubmission(Authentication authentication,
                                          @PathVariable Long submissionId)
            throws ResourceNotFoundException, UnauthorizedException {
        Submission submission = submissionService.getSubmission(submissionId);
        // check owner id
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        if (!submission.getStudentId().equals(user.getId())) {
            throw new UnauthorizedException();
        }
        return modelMapper.map(submission, SubmissionDto.class);
    }

    /**
     * Get test cases and results
     */
    @GetMapping("{submissionId}/detail")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    public List<SubmissionDetailDto> getSubmissionDetails(@PathVariable Long submissionId) {
        return submissionDetailService.getSubmissionDetail(submissionId)
                .stream()
                .map(submissionDetail -> SubmissionDetailDto.builder()
                        .id(submissionDetail.getId())
                        .submissionId(submissionDetail.getSubmissionId())
                        .input(submissionDetail.getTestCase().getInput())
                        .expectedOutput(submissionDetail.getTestCase().getExpectedOutput())
                        .actualOutput(submissionDetail.getActualOutput())
                        .testCaseIsPassed(submissionDetail.getTestCaseIsPassed())
                        .build()
                ).collect(Collectors.toList());
    }

    /**
     * Get own submission details, which include test cases and results
     */
    @GetMapping("own/{submissionId}/detail")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER', 'STUDENT')")
    public List<SubmissionDetailDto> getOwnSubmissionDetails(Authentication authentication,
                                                          @PathVariable Long submissionId) throws BadRequestException, ResourceNotFoundException, UnauthorizedException {
        // check owner id
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        Submission submission = submissionService.getSubmission(submissionId);
        if (!submission.getStudentId().equals(user.getId())) {
            throw new UnauthorizedException();
        }
        return submissionDetailService.getSubmissionDetail(submissionId)
                .stream()
                .map(submissionDetail -> SubmissionDetailDto.builder()
                        .id(submissionDetail.getId())
                        .submissionId(submissionDetail.getSubmissionId())
                        .input(submissionDetail.getTestCase().getInput())
                        .expectedOutput(submissionDetail.getTestCase().getExpectedOutput())
                        .actualOutput(submissionDetail.getActualOutput())
                        .testCaseIsPassed(submissionDetail.getTestCaseIsPassed())
                        .build()
                ).collect(Collectors.toList());
    }

    /**
     * Get test cases and results
     */
    @GetMapping("own/{submissionId}/test-result")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER', 'STUDENT')")
    public SubmissionTestResult getTestResult(Authentication authentication,
                                              @PathVariable Long submissionId)
            throws BadRequestException, ResourceNotFoundException, UnauthorizedException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        // check owner id
        Submission submission = submissionService.getSubmission(submissionId);
        if (!submission.getStudentId().equals(user.getId())) {
            throw new UnauthorizedException();
        }
        return submissionTestResultService.getSubmissionTestResult(submissionId);
    }
}
