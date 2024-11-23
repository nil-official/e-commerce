package com.ecommerce.service;

import com.ecommerce.exception.UserException;
import com.ecommerce.request.LoginRequest;
import com.ecommerce.request.RegisterRequest;
import com.ecommerce.response.AuthResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    void signUp(RegisterRequest registerRequest) throws UserException, MessagingException;

    AuthResponse signIn(LoginRequest loginRequest);

    void verifyAccount(String token);

    void sendResetPasswordEmail(String email, HttpServletRequest request) throws UserException, MessagingException;

    void resetPassword(String token, String newPassword);

}
