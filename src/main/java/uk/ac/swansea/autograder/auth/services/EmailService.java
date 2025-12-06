package uk.ac.swansea.autograder.auth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromAddress;

    @Value("${app.password-reset.baseUrl}")
    private String resetBaseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String token) {
        String resetLink = resetBaseUrl + "?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toEmail);
        message.setSubject("Password Reset Request - Autograder");
        message.setText("Hello,\n\n" +
                "You have requested to reset your password.\n\n" +
                "Click the link below to reset your password:\n" +
                resetLink + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Best regards,\nAutograder Team");

        mailSender.send(message);
    }

    public void sendWelcomeEmail(String toEmail, String username, String temporaryPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toEmail);
        message.setSubject("Welcome to Autograder - Your Account Has Been Created");
        message.setText("Hello,\n\n" +
                "Your Autograder account has been created successfully!\n\n" +
                "Your login credentials:\n" +
                "Username: " + username + "\n" +
                "Password: " + temporaryPassword + "\n\n" +
                "Please login and change your password immediately for security.\n\n" +
                "Best regards,\nAutograder Team");

        mailSender.send(message);
    }
}
