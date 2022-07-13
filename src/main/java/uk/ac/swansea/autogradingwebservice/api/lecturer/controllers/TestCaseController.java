package uk.ac.swansea.autogradingwebservice.api.lecturer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto.TestCaseDto;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.TestCase;
import uk.ac.swansea.autogradingwebservice.api.lecturer.services.TestCaseService;
import uk.ac.swansea.autogradingwebservice.config.MyUserDetails;
import uk.ac.swansea.autogradingwebservice.exceptions.BadRequestException;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/lecturer/problem/{problemId}/test-case")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;

    @GetMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    public List<TestCase> getTestCases(@PathVariable Long problemId) {
        return testCaseService.getAllTestCasesByProblemId(problemId);
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
