package uk.ac.swansea.autograder.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.admin.controllers.dto.NewUserDto;
import uk.ac.swansea.autograder.auth.entities.Role;
import uk.ac.swansea.autograder.auth.entities.User;
import uk.ac.swansea.autograder.auth.repositories.UserRepository;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    public List<User> getLecturers(Pageable pageable)
            throws ResourceNotFoundException {
        return userRepository.findAllUsersByRoleId(roleService.getLecturerRole().getId(), pageable);
    }

    public List<User> getStudents(Pageable pageable)
            throws ResourceNotFoundException {
        return userRepository.findAllUsersByRoleId(roleService.getStudentRole().getId(), pageable);
    }

    public User createLecturer(NewUserDto newUserDto) throws ResourceNotFoundException {
        return createUser(newUserDto, roleService.getLecturerRole());
    }

    public User createStudent(NewUserDto newUserDto) throws ResourceNotFoundException {
        return createUser(newUserDto, roleService.getStudentRole());
    }

    private User createUser(NewUserDto newUserDto, Role role) {
        User user = new User();
        user.setUsername(newUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setEnabled(true);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
