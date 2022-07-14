package uk.ac.swansea.autogradingwebservice.api.admin.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autogradingwebservice.api.admin.controllers.dto.NewUserDto;
import uk.ac.swansea.autogradingwebservice.api.admin.controllers.dto.UserDto;
import uk.ac.swansea.autogradingwebservice.auth.entities.User;
import uk.ac.swansea.autogradingwebservice.auth.services.UserService;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Can create/edit lecturer profile and student profile.
 */
@RestController
@RequestMapping("api/admin/lecturer")
public class AdminLecturerController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> getLecturers(@RequestParam(defaultValue = "0") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize)
            throws ResourceNotFoundException {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return convertToDto(userService.getLecturers(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
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
