package com.ecommerce.service.impl;

import com.ecommerce.exception.InvalidTokenException;
import com.ecommerce.exception.TokenExpiredException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.model.VerifyToken;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.VerifyTokenRepository;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final VerifyTokenRepository verifyTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${frontend.base.url}")
    private String frontendBaseUrl;

    @Override
    public void sendResetPasswordEmail(String email, HttpServletRequest request) throws UserException, MessagingException {
        // Check if user exists
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("No account found with this email!");
        }

        // Generate reset token
        String token = UUID.randomUUID().toString();
        VerifyToken resetToken = new VerifyToken();
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        resetToken.setUser(user);
        verifyTokenRepository.save(resetToken);

        // Construct reset password link using the injected frontendBaseUrl
        String resetLink = frontendBaseUrl + "/reset-password?token=" + token;

        // Send reset password email
        emailService.sendResetPasswordEmail(user.getEmail(), resetLink);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        // Find token in DB
        VerifyToken resetToken = verifyTokenRepository.findByToken(token);
        if (resetToken == null) {
            throw new InvalidTokenException("Invalid password reset token.");
        }

        // Check token expiry
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Password reset token has expired.");
        }

        // Reset password
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete the token
        verifyTokenRepository.delete(resetToken);
    }

}
