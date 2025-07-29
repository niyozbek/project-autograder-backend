package uk.ac.swansea.autograder.api.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.controllers.dto.TestCaseDto;
import uk.ac.swansea.autograder.api.entities.Problem;
import uk.ac.swansea.autograder.api.entities.TestCase;
import uk.ac.swansea.autograder.api.services.ProblemService;
import uk.ac.swansea.autograder.api.services.TestCaseService;
import uk.ac.swansea.autograder.config.MyUserDetails;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.exceptions.UnauthorizedException;

import java.util.List;

@RestController
@RequestMapping("api/test-cases")
public class TestCasesController {
    private final TestCaseService testCaseService;
    private final ProblemService problemService;

    public TestCasesController(TestCaseService testCaseService, ProblemService problemService) {
        this.testCaseService = testCaseService;
        this.problemService = problemService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    public List<TestCase> getTestCases(@RequestParam(required = false) Long problemId,
                                       @RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        List<TestCase> testCases;
        if (problemId != null) {
            testCases = testCaseService.getAllTestCasesByProblemId(problemId, pageable);
        } else {
            testCases = testCaseService.getAllTestCases(pageable);
        }
        return testCases;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    public TestCase addTestCase(Authentication authentication,
                                @Valid @RequestBody TestCaseDto testCaseDto) throws ResourceNotFoundException, UnauthorizedException {
        // check owner id
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        Long problemId = testCaseDto.getProblemId();
        Problem problem = problemService.getProblem(problemId);
        if (!problem.getLecturerId().equals(user.getId())) {
            throw new UnauthorizedException();
        }
        return testCaseService.addTestCase(problemId, testCaseDto);
    }
}
