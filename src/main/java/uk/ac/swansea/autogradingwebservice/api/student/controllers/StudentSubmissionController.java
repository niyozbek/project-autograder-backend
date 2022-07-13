package uk.ac.swansea.autogradingwebservice.api.student.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.swansea.autogradingwebservice.api.student.entities.Submission;
import uk.ac.swansea.autogradingwebservice.api.student.entities.SubmissionDetail;
import uk.ac.swansea.autogradingwebservice.api.student.services.SubmissionService;
import uk.ac.swansea.autogradingwebservice.config.MyUserDetails;
import uk.ac.swansea.autogradingwebservice.exceptions.BadRequestException;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import java.util.List;

/**
 * Get all submissions+
 * Get a specific submission+
 * View every test case of every submission+
 */
@RestController
@RequestMapping("api/student/submission")
public class StudentSubmissionController {
    @Autowired
    private SubmissionService submissionService;

    /**
     * Get list of submitted solutions by the student
     * @return list of submissions
     */
    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<Submission> getSubmissions(Authentication authentication) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return submissionService.getSubmissionsByStudentId(user.getId());
    }
    /**
     * Get list specific submission
     * @return submission
     */
    @GetMapping("{submissionId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Submission getSubmission(Authentication authentication,
                                          @PathVariable Long submissionId) throws BadRequestException, ResourceNotFoundException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return submissionService.getSubmission(submissionId, user.getId());
    }

    /**
     * Get test cases and results
     */
    @GetMapping("{submissionId}/detail")
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<SubmissionDetail> getSubmissionDetails(Authentication authentication,
                                                @PathVariable Long submissionId) throws BadRequestException, ResourceNotFoundException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return submissionService.getSubmissionDetail(submissionId, user.getId());
    }
}
