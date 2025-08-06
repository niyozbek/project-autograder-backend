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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autograder.api.controllers.dto.NewUserDto;
import uk.ac.swansea.autograder.api.controllers.dto.UserDto;
import uk.ac.swansea.autograder.config.MyUserDetails;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.exceptions.UnauthorizedException;
import uk.ac.swansea.autograder.general.entities.User;
import uk.ac.swansea.autograder.general.services.UserService;

import java.util.List;
import java.util.Objects;

/**
 * Can create/update users.
 */
@RestController
@RequestMapping("api/users")
@Tag(name = "Manage users", description = "Can manage users")
public class UsersController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UsersController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_USER')")
    @Operation(summary = "Get all users", description = "Returns a paginated list of users")
    public List<UserDto> getUsers(@RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        List<User> users = userService.getUsers(pageable);
        return modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER')")
    @Operation(summary = "Create new user", description = "Creates a new user account")
    public UserDto createUser(@Valid @RequestBody NewUserDto newUserDto) throws ResourceNotFoundException {
        User user = userService.createUser(newUserDto);
        return modelMapper.map(user, UserDto.class);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('VIEW_USER')")
    @Operation(summary = "Get user", description = "Returns a user")
    public UserDto getUser(@PathVariable Long id) throws ResourceNotFoundException {
        User user = userService.getUser(id);
        return modelMapper.map(user, UserDto.class);
    }

    @GetMapping("own/{id}")
    @PreAuthorize("hasAuthority('VIEW_OWN_USER')")
    @Operation(summary = "Get user", description = "Returns a user")
    public UserDto getOwnUser(Authentication authentication, @PathVariable Long id) throws ResourceNotFoundException, UnauthorizedException {
        // check owner id
        MyUserDetails authUser = (MyUserDetails) authentication.getPrincipal();
        User user = userService.getUser(id);
        if (!Objects.equals(authUser.getId(), user.getId())) {
            throw new UnauthorizedException();
        }

        return modelMapper.map(user, UserDto.class);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @Operation(summary = "Update user", description = "Updates user account")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) throws ResourceNotFoundException {
        User user = userService.updateUser(userDto);
        return modelMapper.map(user, UserDto.class);
    }
}
