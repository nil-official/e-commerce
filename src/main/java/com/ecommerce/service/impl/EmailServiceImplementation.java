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
    public void sendVerificationEmail(String to, String token, final HttpServletRequest request) throws MessagingException {

        String subject = "Email Verification";
        String scheme = request.getScheme();
        String host = request.getServerName();
        int port = request.getServerPort();
        String link = scheme + "://" + host + (port != 80 && port != 443 ? ":" + port : "");

        String content = "<p>Please verify your email by clicking the link below:</p>"
                + "<a href=\"" + link + "/auth/verify?token=" + HtmlUtils.htmlEscape(token) + "\">Verify</a>";

        sendEmail(to, subject, content);
    }

    @Async
    @Override
    public void sendResetPasswordEmail(String toEmail, String resetLink) throws MessagingException {
        // Extract token from the reset link
        String token = resetLink.substring(resetLink.lastIndexOf("=") + 1); // Extracts token after '='

        // Create email content
        String subject = "Reset Your Password";
        String content = "<p>You have requested to reset your password. Click the link below to reset it:</p>"
                + "<a href=\"" + resetLink + "\">Reset Password</a>"
                + "<p><b>Token:</b> " + token + "</p>"
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
