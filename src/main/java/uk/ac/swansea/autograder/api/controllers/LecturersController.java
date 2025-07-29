package uk.ac.swansea.autograder.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.controllers.dto.NewUserDto;
import uk.ac.swansea.autograder.api.controllers.dto.UserDto;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.User;
import uk.ac.swansea.autograder.general.enums.RoleEnum;
import uk.ac.swansea.autograder.general.services.UserService;

import java.util.List;

/**
 * Can create/edit lecturer profile and student profile.
 */
@RestController
@RequestMapping("api/lecturers")
@Tag(name = "Manage lecturers", description = "Admin can manage lecturers")
public class LecturersController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LecturersController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_LECTURER')")
    @Operation(summary = "Get all lecturers", description = "Returns a paginated list of lecturers")
    public List<UserDto> getLecturers(@RequestParam(defaultValue = "0") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize)
            throws ResourceNotFoundException {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        List<User> users = userService.getUsersByRole(RoleEnum.LECTURER, pageable);
        return modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_LECTURER')")
    @Operation(summary = "Create new lecturer", description = "Creates a new lecturer account")
    public UserDto createLecturer(@Valid @RequestBody NewUserDto newUserDto)
            throws ResourceNotFoundException {
        User user = userService.createUserWithRole(newUserDto, RoleEnum.LECTURER);
        return modelMapper.map(user, UserDto.class);
    }
}
