package com.example.onlinebookshop.services.email;

import com.example.onlinebookshop.services.utils.EmailLinkParameterProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.example.onlinebookshop.constants.Constants.FIRST_POSITION;
import static com.example.onlinebookshop.constants.Constants.SECOND_POSITION;
import static com.example.onlinebookshop.constants.Constants.SPLITERATOR;
import static com.example.onlinebookshop.constants.security.SecurityConstants.CONFIRM_REGISTRATION_BODY;
import static com.example.onlinebookshop.constants.security.SecurityConstants.CONFIRM_REGISTRATION_SUBJECT;

@Service
public class RegisterConfirmEmailService extends EmailService {
    @Value("${server.path}")
    private String serverPath;

    public RegisterConfirmEmailService(EmailLinkParameterProvider emailLinkParameterProvider) {
        super(emailLinkParameterProvider);
    }

    public void sendRegisterConfirmEmail(String toEmail) {
        String[] paramTokenPair = getEmailLinkParameterProvider().formRandomParamTokenPair(toEmail);
        sendMessage(toEmail, CONFIRM_REGISTRATION_SUBJECT,
                CONFIRM_REGISTRATION_BODY + System.lineSeparator()
                        + serverPath + "/auth/register-success?"
                        + paramTokenPair[FIRST_POSITION]
                        + SPLITERATOR
                        + paramTokenPair[SECOND_POSITION]);
    }
}
