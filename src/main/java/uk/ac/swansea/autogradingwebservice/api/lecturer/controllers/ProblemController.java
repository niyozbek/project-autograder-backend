package uk.ac.swansea.autogradingwebservice.api.lecturer.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto.ProblemBriefDto;
import uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto.ProblemDto;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;
import uk.ac.swansea.autogradingwebservice.api.lecturer.services.ProblemService;
import uk.ac.swansea.autogradingwebservice.config.MyUserDetails;
import uk.ac.swansea.autogradingwebservice.exceptions.BadRequestException;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create problem, so that students can submit a code for it+
 * TODO: Can view grade of each student in terms of valid test cases and percentage
 * Can view all problems created previously+
 * Can't view problems created by other lecturers+
 */
@RestController
@RequestMapping("api/lecturer/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    public List<ProblemBriefDto> getProblems(Authentication authentication) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return convertToDto(problemService.getProblemsByLecturerId(user.getId()));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('LECTURER')")
    public ProblemDto getProblem(Authentication authentication,
                                 @PathVariable Long id)
            throws ResourceNotFoundException, BadRequestException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return convertToDto(problemService.getProblemByLecturerId(id, user.getId()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('LECTURER')")
    public ProblemDto createProblem(Authentication authentication,
                                    @Valid @RequestBody ProblemDto problemDto) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        problemDto.setLecturerId(user.getId());
        return convertToDto(problemService.createProblem(problemDto));
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
