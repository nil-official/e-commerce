package com.ecommerce.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

public interface EmailService {

    void sendVerificationEmail(String to, String token, final HttpServletRequest request) throws MessagingException;

    void sendResetPasswordEmail(String toEmail, String resetLink) throws MessagingException;

}
