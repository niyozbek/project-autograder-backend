package uk.ac.swansea.autograder.general.services;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.controllers.dto.*;
import uk.ac.swansea.autograder.auth.services.EmailService;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.entities.User;
import uk.ac.swansea.autograder.general.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EmailService emailService;

    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
            RoleService roleService, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.emailService = emailService;
    }

    public List<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).toList();
    }

    public User createUser(NewUserDto newUserDto) throws ResourceNotFoundException {
        User user = new User();
        user.setUsername(newUserDto.getUsername());
        user.setFullname(newUserDto.getFullname());
        user.setEmail(newUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setEnabled(true);
        User savedUser = userRepository.save(user);

        // Send welcome email with credentials
        try {
            emailService.sendWelcomeEmail(newUserDto.getEmail(), newUserDto.getUsername(), newUserDto.getPassword());
            System.out.println("✓ Welcome email sent successfully to: " + newUserDto.getEmail());
        } catch (Exception e) {
            // Log full error for debugging
            System.err.println("✗ Failed to send welcome email to " + newUserDto.getEmail());
            System.err.println("  Error: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }

        return savedUser;
    }

    public User getUser(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public User updateUser(UserDto userDto) throws ResourceNotFoundException {
        User user = getUser(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setFullname(userDto.getFullname());
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    public User updateUserRoles(UserDto userDto) throws ResourceNotFoundException {
        User user = getUser(userDto.getId());
        Set<Role> roles = new HashSet<>();
        for (RoleBriefDto roleBriefDto : userDto.getRoles()) {
            Role role = roleService.getRole(roleBriefDto.getId());
            roles.add(role);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    // public User disableUser(Long id) throws ResourceNotFoundException {
    // User user = getUser(id);
    // user.setEnabled(false);
    // return userRepository.save(user);
    // }
    //
    // public User enableUser(Long id) throws ResourceNotFoundException {
    // User user = getUser(id);
    // user.setEnabled(true);
    // return userRepository.save(user);
    // }

}
