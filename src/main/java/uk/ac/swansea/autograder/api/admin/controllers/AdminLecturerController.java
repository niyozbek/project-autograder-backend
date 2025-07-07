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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Can create/edit lecturer profile and student profile.
 */
@RestController
@RequestMapping("api/admin/lecturer")
@Tag(name = "Manage lecturers", description = "Admin can manage lecturers")
public class AdminLecturerController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all lecturers", description = "Returns a paginated list of lecturers")
    public List<UserDto> getLecturers(@RequestParam(defaultValue = "0") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize)
            throws ResourceNotFoundException {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return convertToDto(userService.getLecturers(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create new lecturer", description = "Creates a new lecturer account")
    public UserDto createLecturer(@Valid @RequestBody NewUserDto newUserDto)
            throws ResourceNotFoundException {
        return convertToDto(userService.createLecturer(newUserDto));
    }

    private List<UserDto> convertToDto(List<User> userList) {
        return userList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
