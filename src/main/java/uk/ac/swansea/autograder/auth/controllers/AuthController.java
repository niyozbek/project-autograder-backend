package uk.ac.swansea.autograder.auth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.swansea.autograder.auth.controllers.dto.ForgotPasswordDto;
import uk.ac.swansea.autograder.auth.controllers.dto.LoginDto;
import uk.ac.swansea.autograder.auth.controllers.dto.LoginResponseDto;
import uk.ac.swansea.autograder.auth.controllers.dto.MessageResponse;
import uk.ac.swansea.autograder.auth.controllers.dto.ResetPasswordDto;
import uk.ac.swansea.autograder.auth.services.AuthService;
import uk.ac.swansea.autograder.auth.services.PasswordResetService;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.exceptions.UnauthorizedException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication", description = "API endpoints for authentication")
public class AuthController {
    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    public AuthController(AuthService authService, PasswordResetService passwordResetService) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("login")
    @Operation(summary = "Login user", description = "Authenticates a user and returns JWT token along with user role and username")
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) throws UnauthorizedException {
        String token = authService.login(loginDto);
        String role = authService.getUserRoleFromJWT(token);
        return LoginResponseDto.builder()
                .username(loginDto.getUsername())
                .role(role)
                .token(token)
                .build();
    }

    @PostMapping("forgot-password")
    @Operation(summary = "Request password reset", description = "Sends a password reset email to the user if the email exists")
    public ResponseEntity<MessageResponse> forgotPassword(@Valid @RequestBody ForgotPasswordDto dto) {
        try {
            passwordResetService.requestPasswordReset(dto.getEmail());
        } catch (ResourceNotFoundException e) {
            // Don't reveal if email exists or not for security
        }
        return ResponseEntity.ok(new MessageResponse("If the email exists, a password reset link has been sent."));
    }

    @PostMapping("reset-password")
    @Operation(summary = "Reset password", description = "Resets the user's password using the token from the email")
    public ResponseEntity<MessageResponse> resetPassword(@Valid @RequestBody ResetPasswordDto dto)
            throws ResourceNotFoundException {
        passwordResetService.resetPassword(dto.getToken(), dto.getNewPassword());
        return ResponseEntity.ok(new MessageResponse("Password has been reset successfully."));
    }
}
