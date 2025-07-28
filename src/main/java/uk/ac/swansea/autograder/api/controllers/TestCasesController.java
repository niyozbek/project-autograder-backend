package uk.ac.swansea.autograder.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.controllers.dto.TestCaseDto;
import uk.ac.swansea.autograder.config.MyUserDetails;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.exceptions.UnauthorizedException;
import uk.ac.swansea.autograder.general.entities.Problem;
import uk.ac.swansea.autograder.general.entities.TestCase;
import uk.ac.swansea.autograder.general.services.ProblemService;
import uk.ac.swansea.autograder.general.services.TestCaseService;

import java.util.List;

@RestController
@RequestMapping("api/test-cases")
public class TestCasesController {
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private ProblemService problemService;

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
                                @Valid @RequestBody TestCaseDto testCaseDto) throws BadRequestException, ResourceNotFoundException, UnauthorizedException {
        // check owner id
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        Long problemId = testCaseDto.getProblemId();
        Problem problem = problemService.getProblem(problemId);
        if (!problem.getLecturerId().equals(user.getId())) {
            throw new UnauthorizedException();
        }
        return testCaseService.addTestCase(problemId, testCaseDto, user.getId());
    }
}
