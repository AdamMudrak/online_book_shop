package com.example.onlinebookshop.services.email;

import static com.example.onlinebookshop.constants.security.SecurityConstants.INITIATE_RANDOM_PASSWORD_BODY;
import static com.example.onlinebookshop.constants.security.SecurityConstants.INITIATE_RANDOM_PASSWORD_SUBJECT;
import static com.example.onlinebookshop.constants.security.SecurityConstants.RANDOM_PASSWORD_BODY;
import static com.example.onlinebookshop.constants.security.SecurityConstants.RANDOM_PASSWORD_BODY_2;
import static com.example.onlinebookshop.constants.security.SecurityConstants.RANDOM_PASSWORD_SUBJECT;

import com.example.onlinebookshop.services.utils.ActionTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEmailService extends EmailService {
    private final ActionTokenUtil actionTokenUtil;
    @Value("${server.path}")
    private String serverPath;

    public void sendInitiatePasswordReset(String toEmail) {
        sendMessage(toEmail, INITIATE_RANDOM_PASSWORD_SUBJECT,
                INITIATE_RANDOM_PASSWORD_BODY
                        + System.lineSeparator()
                        + serverPath + "/auth/reset-password?token="
                        + actionTokenUtil.generateActionToken(toEmail));
    }

    public void sendResetPassword(String toEmail, String randomPassword) {
        sendMessage(toEmail, RANDOM_PASSWORD_SUBJECT,
                RANDOM_PASSWORD_BODY
                        + System.lineSeparator()
                        + randomPassword
                        + System.lineSeparator()
                        + RANDOM_PASSWORD_BODY_2);
    }
}
