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
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.controllers.dto.NewUserDto;
import uk.ac.swansea.autograder.api.controllers.dto.UserDto;
import uk.ac.swansea.autograder.general.entities.User;
import uk.ac.swansea.autograder.general.services.UserService;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Can create/edit lecturer profile and student profile.
 */
@RestController
@RequestMapping("api/students")
@Tag(name = "Manage students", description = "Admin can manage students")
public class StudentsController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Get all students",
            description = "Returns a paginated list of students. Results are sorted by ID in descending order."
    )
    public List<UserDto> getStudents(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "10") Integer pageSize)
            throws ResourceNotFoundException {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        List<User> users = userService.getStudents(pageable);
        return modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Create new student",
            description = "Creates a new student account with the provided user details"
    )
    public UserDto createStudent(@Valid @RequestBody NewUserDto newUserDto)
            throws ResourceNotFoundException {
        User user = userService.createStudent(newUserDto);
        return modelMapper.map(user, UserDto.class);
    }
}
