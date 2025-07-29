package uk.ac.swansea.autograder.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uk.ac.swansea.autograder.general.entities.Role;
import uk.ac.swansea.autograder.general.entities.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
            .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public String getRoleName() {
        Role role = user.getRoles().stream()
                .findFirst()
                .orElse(null);
        if (role == null) {
            return "";
        }
        return role.getName();
    }
}