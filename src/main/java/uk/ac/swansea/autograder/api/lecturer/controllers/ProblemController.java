package uk.ac.swansea.autograder.api.lecturer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.lecturer.controllers.dto.ProblemBriefDto;
import uk.ac.swansea.autograder.api.lecturer.controllers.dto.ProblemDto;
import uk.ac.swansea.autograder.api.lecturer.entities.Problem;
import uk.ac.swansea.autograder.api.lecturer.services.ProblemService;
import uk.ac.swansea.autograder.config.MyUserDetails;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create problem, so that students can submit a code for it+
 * Can view submissions for each problem
 * Can view all problems created previously+
 * Can't view problems created by other lecturers+
 */
@RestController
@RequestMapping("api/lecturer/problem")
@Tag(name = "Manage problems", description = "Lecturer can manage problems")
public class ProblemController {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    @Operation(
            summary = "Get all problems",
            description = "Returns a paginated list of problems created by the authenticated lecturer. Results are sorted by ID in descending order."
    )
    public List<ProblemBriefDto> getProblems(Authentication authentication,
                                             @RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return convertToDto(problemService.getProblemsByLecturerId(user.getId(), pageable));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('LECTURER')")
    @Operation(
            summary = "Get problem by ID",
            description = "Returns detailed information about a specific problem. Only accessible by the lecturer who created it."
    )
    public ProblemDto getProblem(Authentication authentication,
                                 @PathVariable Long id)
            throws ResourceNotFoundException, BadRequestException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return convertToDto(problemService.getProblemByLecturerId(id, user.getId()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    @Operation(
            summary = "Create new problem",
            description = "Creates a new programming problem with the provided details. The authenticated lecturer will be set as the creator."
    )
    public ProblemDto createProblem(Authentication authentication,
                                    @Valid @RequestBody ProblemDto problemDto) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        problemDto.setLecturerId(user.getId());
        return convertToDto(problemService.createProblem(problemDto));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('LECTURER')")
    @Operation(
            summary = "Update problem",
            description = "Updates an existing problem. Only the lecturer who created the problem can modify it."
    )
    public ProblemDto updateProblem(Authentication authentication,
                                    @PathVariable Long id,
                                    @Valid @RequestBody ProblemDto problemDto)
            throws ResourceNotFoundException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        problemDto.setLecturerId(user.getId());
        return convertToDto(problemService.updateProblem(id, problemDto));
    }


    private List<ProblemBriefDto> convertToDto(List<Problem> problemList) {
        return problemList.stream()
                .map(this::convertToBriefDto)
                .collect(Collectors.toList());
    }

    private ProblemBriefDto convertToBriefDto(Problem problem) {
        return modelMapper.map(problem, ProblemBriefDto.class);
    }

    private ProblemDto convertToDto(Problem problem) {
        return modelMapper.map(problem, ProblemDto.class);
    }
}
