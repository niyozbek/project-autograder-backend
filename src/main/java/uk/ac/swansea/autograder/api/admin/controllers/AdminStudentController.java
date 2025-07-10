package uk.ac.swansea.autograder.api.admin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.admin.controllers.dto.NewUserDto;
import uk.ac.swansea.autograder.api.admin.controllers.dto.UserDto;
import uk.ac.swansea.autograder.general.entities.User;
import uk.ac.swansea.autograder.general.services.UserService;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Can create/edit lecturer profile and student profile.
 */
@RestController
@RequestMapping("api/admin/student")
@Tag(name = "Manage students", description = "Admin can manage students")
public class AdminStudentController {
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
        return convertToDto(userService.getStudents(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Create new student",
            description = "Creates a new student account with the provided user details"
    )
    public UserDto createStudent(@Valid @RequestBody NewUserDto newUserDto)
            throws ResourceNotFoundException {
        return convertToDto(userService.createStudent(newUserDto));
    }

    private List<UserDto> convertToDto(List<User> userList) {
        return userList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
