package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dtos.authentication.request.LoginRequest;
import com.example.onlinebookshop.dtos.authentication.request.PasswordChangeRequest;
import com.example.onlinebookshop.dtos.authentication.request.RegistrationRequest;
import com.example.onlinebookshop.dtos.authentication.response.LoginResponse;
import com.example.onlinebookshop.dtos.authentication.response.PasswordChangeResponse;
import com.example.onlinebookshop.dtos.authentication.response.PasswordResetLinkResponse;
import com.example.onlinebookshop.dtos.authentication.response.RegistrationConfirmationResponse;
import com.example.onlinebookshop.dtos.authentication.response.RegistrationResponse;
import com.example.onlinebookshop.dtos.authentication.response.ResetLinkSentResponse;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.LoginException;
import com.example.onlinebookshop.exceptions.PasswordMismatchException;
import com.example.onlinebookshop.exceptions.RegistrationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    LoginResponse authenticateUser(LoginRequest requestDto,
                                   HttpServletResponse httpServletResponse) throws LoginException;

    PasswordResetLinkResponse sendPasswordResetLink(String emailOrUsername) throws LoginException;

    ResetLinkSentResponse confirmResetPassword(HttpServletRequest request);

    PasswordChangeResponse changePassword(User user,
                                          PasswordChangeRequest userSetNewPasswordRequestDto)
                                                                throws PasswordMismatchException;

    RegistrationResponse register(RegistrationRequest requestDto)
                                                                    throws RegistrationException;

    RegistrationConfirmationResponse confirmRegistration(HttpServletRequest request);
}
