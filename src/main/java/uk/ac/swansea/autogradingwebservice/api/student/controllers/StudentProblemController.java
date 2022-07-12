package uk.ac.swansea.autogradingwebservice.api.student.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;
import uk.ac.swansea.autogradingwebservice.api.lecturer.services.ProblemService;
import uk.ac.swansea.autogradingwebservice.api.student.controllers.dto.SubmissionDto;
import uk.ac.swansea.autogradingwebservice.api.student.entities.Submission;
import uk.ac.swansea.autogradingwebservice.api.student.entities.SubmissionDetail;
import uk.ac.swansea.autogradingwebservice.api.student.services.SubmissionService;
import uk.ac.swansea.autogradingwebservice.api.student.services.dto.RuntimeDto;
import uk.ac.swansea.autogradingwebservice.config.MyUserDetails;
import uk.ac.swansea.autogradingwebservice.exceptions.BadRequestException;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/student/problem")
public class StudentProblemController {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<Problem> getProblems() {
        return problemService.getProblems();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Problem getProblem(@PathVariable Long id) throws ResourceNotFoundException {
        return problemService.getProblem(id);
    }

    @GetMapping("{id}/runtime")
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<RuntimeDto> getProblemRuntime(@PathVariable Long id) {
        return submissionService.getRuntime(id);
    }

    /**
     * Submit solution to a problem
     * @param id
     * @param submissionDto
     */
    @PostMapping("{id}/submit")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void submitSolution(Authentication authentication,
                               @PathVariable Long id,
                               @Valid @RequestBody SubmissionDto submissionDto) throws ResourceNotFoundException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        submissionService.submitSolution(id, submissionDto, user.getId());
    }

    /**
     * Get list of submitted solutions by the student for a specific problem
     * @return
     */
    @GetMapping("{id}/submission")
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<Submission> getSubmissions(Authentication authentication, @PathVariable Long id) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return submissionService.getSubmissionsByProblemIdAndStudentId(id, user.getId());
    }

    /**
     * Get list specific submission
     * @return
     */
    @GetMapping("{problemId}/submission/{submissionId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Submission getSubmission(Authentication authentication,
                                          @PathVariable Long problemId,
                                          @PathVariable Long submissionId) throws BadRequestException, ResourceNotFoundException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return submissionService.getSubmission(submissionId, problemId, user.getId());
    }

    /**
     * Get test cases and results
     */
    @GetMapping("{problemId}/submission/{submissionId}/detail")
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<SubmissionDetail> getSubmissionDetails(Authentication authentication,
                                                @PathVariable Long problemId,
                                                @PathVariable Long submissionId) throws BadRequestException, ResourceNotFoundException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return submissionService.getSubmissionDetail(submissionId, problemId, user.getId());
    }
}
