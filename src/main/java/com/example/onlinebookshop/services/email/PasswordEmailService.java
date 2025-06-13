package com.example.onlinebookshop.services.email;

import com.example.onlinebookshop.services.utils.EmailLinkParameterProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.example.onlinebookshop.constants.Constants.FIRST_POSITION;
import static com.example.onlinebookshop.constants.Constants.SECOND_POSITION;
import static com.example.onlinebookshop.constants.Constants.SPLITERATOR;
import static com.example.onlinebookshop.constants.security.SecurityConstants.INITIATE_RANDOM_PASSWORD_BODY;
import static com.example.onlinebookshop.constants.security.SecurityConstants.INITIATE_RANDOM_PASSWORD_SUBJECT;
import static com.example.onlinebookshop.constants.security.SecurityConstants.RANDOM_PASSWORD_BODY;
import static com.example.onlinebookshop.constants.security.SecurityConstants.RANDOM_PASSWORD_BODY_2;
import static com.example.onlinebookshop.constants.security.SecurityConstants.RANDOM_PASSWORD_SUBJECT;

@Service
public class PasswordEmailService extends EmailService {
    @Value("${server.path}")
    private String serverPath;

    public PasswordEmailService(EmailLinkParameterProvider emailLinkParameterProvider) {
        super(emailLinkParameterProvider);
    }

    public void sendInitiatePasswordReset(String toEmail) {
        String[] paramTokenPair = getEmailLinkParameterProvider().formRandomParamTokenPair(toEmail);

        sendMessage(toEmail, INITIATE_RANDOM_PASSWORD_SUBJECT,
                INITIATE_RANDOM_PASSWORD_BODY + System.lineSeparator()
                        + serverPath + "/auth/reset-password?"
                        + paramTokenPair[FIRST_POSITION]
                        + SPLITERATOR
                        + paramTokenPair[SECOND_POSITION]);
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
