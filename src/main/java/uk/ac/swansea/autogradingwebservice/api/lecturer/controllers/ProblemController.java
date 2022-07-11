package uk.ac.swansea.autogradingwebservice.api.lecturer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto.ProblemDto;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;
import uk.ac.swansea.autogradingwebservice.api.lecturer.services.ProblemService;
import uk.ac.swansea.autogradingwebservice.config.MyUserDetails;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/lecturer/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('LECTURER')")
    public Problem getProblem(@PathVariable Long id) throws ResourceNotFoundException {
        return problemService.getProblem(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    public Problem createProblem(Authentication authentication, @Valid @RequestBody ProblemDto problemDto) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        problemDto.setLecturer_id(user.getId());
        return problemService.createProblem(problemDto);
    }
}
