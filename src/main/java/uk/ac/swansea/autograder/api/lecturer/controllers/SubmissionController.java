package uk.ac.swansea.autograder.api.lecturer.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.lecturer.controllers.dto.SubmissionBriefDto;
import uk.ac.swansea.autograder.api.lecturer.controllers.dto.SubmissionDetailDto;
import uk.ac.swansea.autograder.api.lecturer.controllers.dto.SubmissionDto;
import uk.ac.swansea.autograder.general.entities.Submission;
import uk.ac.swansea.autograder.general.services.SubmissionDetailService;
import uk.ac.swansea.autograder.general.services.SubmissionService;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Get all submissions+
 * Get a specific submission+
 * View every test case of every submission+
 */
@RestController
@RequestMapping("api/lecturer")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private SubmissionDetailService submissionDetailService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get list of submitted solutions of a specific problem
     *
     * @return list of submissions
     */
    @GetMapping("problem/{problemId}/submission")
    @PreAuthorize("hasAuthority('LECTURER')")
    public List<SubmissionBriefDto> getSubmissions(@PathVariable Long problemId,
                                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        List<Submission> submissionList = submissionService
                .getSubmissionsByProblemId(problemId, pageable);
        return convertToDto(submissionList);
    }

    /**
     * Get list specific submission
     *
     * @return submission
     */
    @GetMapping("submission/{submissionId}")
    @PreAuthorize("hasAuthority('LECTURER')")
    public SubmissionDto getSubmission(@PathVariable Long submissionId)
            throws ResourceNotFoundException {
        Submission submission = submissionService.getSubmission(submissionId);
        return convertToDto(submission);
    }

    /**
     * Get test cases and results
     */
    @GetMapping("submission/{submissionId}/detail")
    @PreAuthorize("hasAuthority('LECTURER')")
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

    private List<SubmissionBriefDto> convertToDto(List<Submission> submissionList) {
        return submissionList.stream()
                .map(this::convertToBriefDto)
                .collect(Collectors.toList());
    }

    private SubmissionBriefDto convertToBriefDto(Submission submission) {
        return modelMapper.map(submission, SubmissionBriefDto.class);
    }

    private SubmissionDto convertToDto(Submission problem) {
        return modelMapper.map(problem, SubmissionDto.class);
    }
}
