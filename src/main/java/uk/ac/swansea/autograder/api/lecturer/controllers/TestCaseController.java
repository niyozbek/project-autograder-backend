package uk.ac.swansea.autograder.api.lecturer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.lecturer.controllers.dto.TestCaseDto;
import uk.ac.swansea.autograder.general.entities.TestCase;
import uk.ac.swansea.autograder.general.services.TestCaseService;
import uk.ac.swansea.autograder.config.MyUserDetails;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/lecturer/problem/{problemId}/test-case")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;

    @GetMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    public List<TestCase> getTestCases(@PathVariable Long problemId,
                                       @RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return testCaseService.getAllTestCasesByProblemId(problemId, pageable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    public TestCase addTestCase(Authentication authentication,
                               @PathVariable Long problemId,
                               @Valid @RequestBody TestCaseDto testCaseDto) throws BadRequestException, ResourceNotFoundException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        testCaseDto.setProblemId(problemId);
        return testCaseService.addTestCase(testCaseDto, user.getId());
    }
}
