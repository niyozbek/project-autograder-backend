package uk.ac.swansea.autograder.config;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.general.entities.User;
import uk.ac.swansea.autograder.general.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

}
