package uk.ac.swansea.autograder.api.student.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.general.entities.Problem;
import uk.ac.swansea.autograder.general.services.ProblemService;
import uk.ac.swansea.autograder.api.student.controllers.dto.ProblemBriefDto;
import uk.ac.swansea.autograder.api.student.controllers.dto.ProblemDto;
import uk.ac.swansea.autograder.api.student.controllers.dto.SubmissionDto;
import uk.ac.swansea.autograder.general.entities.Submission;
import uk.ac.swansea.autograder.general.services.SubmissionMainService;
import uk.ac.swansea.autograder.general.services.dto.RuntimeDto;
import uk.ac.swansea.autograder.config.MyUserDetails;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View all problems
 * View a problem
 * Submit the code+
 * Get previous submissions to a specific problem
 */
@RestController
@RequestMapping("api/student/problem")
public class StudentProblemController {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private SubmissionMainService submissionMainService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<ProblemBriefDto> getProblems(@RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return convertToDto(problemService.getProblems(pageable));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ProblemDto getProblem(@PathVariable Long id) throws ResourceNotFoundException {
        return convertToDto(problemService.getProblem(id));
    }

    @GetMapping("{id}/runtime")
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<RuntimeDto> getProblemRuntime(@PathVariable Long id) {
        return submissionMainService.getRuntime(id);
    }

    /**
     * Submit solution to a problem
     *
     * @param id            problemId
     * @param submissionDto submission body
     */
    @PostMapping("{id}/submit")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Submission submitSolution(Authentication authentication,
                                     @PathVariable Long id,
                                     @Valid @RequestBody SubmissionDto submissionDto)
            throws ResourceNotFoundException, BadRequestException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return submissionMainService.submitSolution(id, submissionDto, user.getId());
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
