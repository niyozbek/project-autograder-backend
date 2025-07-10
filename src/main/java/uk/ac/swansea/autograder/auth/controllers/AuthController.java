package uk.ac.swansea.autograder.auth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.swansea.autograder.auth.controllers.dto.LoginDto;
import uk.ac.swansea.autograder.auth.controllers.dto.LoginResponseDto;
import uk.ac.swansea.autograder.auth.services.AuthService;
import uk.ac.swansea.autograder.exceptions.UnauthorizedException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication", description = "API endpoints for authentication")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    @Operation(
            summary = "Login user",
            description = "Authenticates a user and returns JWT token along with user role and username"
    )
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) throws UnauthorizedException {
        String token = authService.login(loginDto);
        String role = authService.getUserRoleFromJWT(token);
        return LoginResponseDto.builder()
                .username(loginDto.getUsername())
                .role(role)
                .token(token)
                .build();
    }
}
