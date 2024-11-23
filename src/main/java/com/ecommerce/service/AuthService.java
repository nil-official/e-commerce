package com.ecommerce.service;

import com.ecommerce.exception.UserException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    void sendResetPasswordEmail(String email, HttpServletRequest request) throws UserException, MessagingException;

    void resetPassword(String token, String newPassword);

}
