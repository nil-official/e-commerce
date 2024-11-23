package com.ecommerce.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

public interface EmailService {

    void sendVerificationEmail(String toEmail, String verifyLink) throws MessagingException;

    void sendResetPasswordEmail(String toEmail, String resetLink) throws MessagingException;

}
