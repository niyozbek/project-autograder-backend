package uk.ac.swansea.autograder.auth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.swansea.autograder.auth.entities.PasswordResetToken;
import uk.ac.swansea.autograder.auth.repositories.PasswordResetTokenRepository;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;
import uk.ac.swansea.autograder.general.entities.User;
import uk.ac.swansea.autograder.general.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.password-reset.tokenExpirationMinutes}")
    private int tokenExpirationMinutes;

    public PasswordResetService(
            PasswordResetTokenRepository tokenRepository,
            UserRepository userRepository,
            EmailService emailService,
            BCryptPasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void requestPasswordReset(String email) throws ResourceNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException();
        }

        // Delete any existing tokens for this user
        tokenRepository.deleteByUserId(user.getId());

        // Create new token
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(tokenExpirationMinutes));

        tokenRepository.save(resetToken);

        // Send email
        emailService.sendPasswordResetEmail(email, token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) throws ResourceNotFoundException {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(ResourceNotFoundException::new);

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new IllegalStateException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete the used token
        tokenRepository.delete(resetToken);
    }
}
