package uk.ac.swansea.autograder.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.auth.controllers.dto.LoginDto;
import uk.ac.swansea.autograder.config.JwtTokenProvider;
import uk.ac.swansea.autograder.exceptions.UnauthorizedException;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    public String login(LoginDto loginDto) throws UnauthorizedException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateUserToken(authentication);
    }

    public String getUserRoleFromJWT(String jwt) {
        return tokenProvider.getUserRoleFromJWT(jwt);
    }
}
