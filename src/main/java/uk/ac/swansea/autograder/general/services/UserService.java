package uk.ac.swansea.autograder.general.services;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.controllers.dto.NewUserDto;
import uk.ac.swansea.autograder.api.controllers.dto.UserDto;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.entities.User;
import uk.ac.swansea.autograder.general.enums.RoleEnum;
import uk.ac.swansea.autograder.general.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public List<User> getUsersByRole(RoleEnum roleEnum, Pageable pageable) throws ResourceNotFoundException {
        Role role = roleService.getRoleByName(roleEnum.name());
        return userRepository.findAllUsersByRoleId(role.getId(), pageable);
    }

    public List<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).toList();
    }

    public User createUserWithRole(NewUserDto newUserDto, RoleEnum roleEnum) throws ResourceNotFoundException {
        User user = createUser(newUserDto);
        Role role = roleService.getRoleByName(roleEnum.name());
        return assignRole(user, role);
    }

    public User createUser(NewUserDto newUserDto) {
        User user = new User();
        user.setUsername(newUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User assignRole(User user, Role role) {
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public User getUser(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public User updateUser(UserDto userDto) throws ResourceNotFoundException {
        User user = getUser(userDto.getId());
        user.setUsername(userDto.getUsername());
        return userRepository.save(user);
    }
}
