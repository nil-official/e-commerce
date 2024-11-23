package com.ecommerce.service.impl;

import com.ecommerce.exception.EmailSendingException;
import com.ecommerce.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@AllArgsConstructor
public class EmailServiceImplementation implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendVerificationEmail(String toEmail, String verifyLink) throws MessagingException {
        // Create email content
        String subject = "Email Verification";
        String content = "<p>Please verify your email by clicking the link below:</p>"
                + "<a href=\"" + verifyLink + "\">Verify Email</a>";

        // Send the email
        sendEmail(toEmail, subject, content);
    }

    @Async
    @Override
    public void sendResetPasswordEmail(String toEmail, String resetLink) throws MessagingException {
        // Create email content
        String subject = "Reset Your Password";
        String content = "<p>You have requested to reset your password. Click the link below to reset it:</p>"
                + "<a href=\"" + resetLink + "\">Reset Password</a>"
                + "<p><b>Note:</b> This link is valid for 1 hour only.</p>";

        // Send the email
        sendEmail(toEmail, subject, content);
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // Set content as HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendingException("Failed to send email to " + to, e);
        }
    }

}
