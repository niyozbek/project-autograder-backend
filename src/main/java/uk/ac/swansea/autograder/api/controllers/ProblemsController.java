package uk.ac.swansea.autograder.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.controllers.dto.*;
import uk.ac.swansea.autograder.exceptions.UnauthorizedException;
import uk.ac.swansea.autograder.general.entities.Problem;
import uk.ac.swansea.autograder.general.services.ProblemService;
import uk.ac.swansea.autograder.config.MyUserDetails;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;
import uk.ac.swansea.autograder.general.services.SubmissionMainService;
import uk.ac.swansea.autograder.general.services.dto.RuntimeDto;

import java.util.List;

/**
 * Create a problem so that students can submit a code for it+
 * Can view submissions for each problem
 * Can view all problems created previously+
 * Can't view problems created by other lecturers+
 */
@RestController
@RequestMapping("api/problems")
@Tag(name = "Manage problems", description = "Lecturer can manage problems")
public class ProblemsController {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private SubmissionMainService submissionMainService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT', 'LECTURER')")
    public List<ProblemBriefDto> getProblems(@RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        List<Problem> problems = problemService.getProblems(pageable);
        return modelMapper.map(problems, new TypeToken<List<ProblemBriefDto>>() {}.getType());
    }

    @GetMapping("own")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @Operation(
            summary = "Get all problems",
            description = "Returns a paginated list of problems created by the authenticated lecturer. Results are sorted by ID in descending order."
    )
    public List<ProblemBriefDto> getProblems(Authentication authentication,
                                             @RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        List<Problem> problems = problemService.getProblemsByLecturerId(user.getId(), pageable);
        return modelMapper.map(problems, new TypeToken<List<ProblemBriefDto>>() {}.getType());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @Operation(
            summary = "Create new problem",
            description = "Creates a new programming problem with the provided details. The authenticated lecturer will be set as the creator."
    )
    public ProblemDto createProblem(Authentication authentication,
                                    @Valid @RequestBody ProblemDto problemDto) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        problemDto.setLecturerId(user.getId());
        Problem problem = problemService.createProblem(problemDto);
        return modelMapper.map(problem, ProblemDto.class);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER', 'STUDENT')")
    @Operation(
            summary = "Get problem by ID",
            description = "Returns detailed information about a specific problem."
    )
    public ProblemDto getProblem(@PathVariable Long id) throws ResourceNotFoundException {
        Problem problem = problemService.getProblem(id);
        return modelMapper.map(problem, ProblemDto.class);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @Operation(
            summary = "Update problem",
            description = "Updates an existing problem. Only the user who created the problem can modify it."
    )
    public ProblemDto updateProblem(Authentication authentication,
                                    @PathVariable Long id,
                                    @Valid @RequestBody ProblemDto problemDto)
            throws ResourceNotFoundException, UnauthorizedException {
        // check owner id
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        Problem problem = problemService.getProblem(id);
        if (!problem.getLecturerId().equals(user.getId())) {
            throw new UnauthorizedException();
        }
        // update
        problem = problemService.updateProblem(id, problemDto);
        return modelMapper.map(problem, ProblemDto.class);
    }

    @GetMapping("{id}/runtime")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER', 'STUDENT')")
    public List<RuntimeDto> getProblemRuntime(@PathVariable Long id) {
        return submissionMainService.getRuntime(id);
    }
}
