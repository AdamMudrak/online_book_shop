package com.example.onlinebookshop.services.email;

import static com.example.onlinebookshop.constants.Constants.FIRST_POSITION;
import static com.example.onlinebookshop.constants.Constants.SECOND_POSITION;
import static com.example.onlinebookshop.constants.Constants.SPLITERATOR;
import static com.example.onlinebookshop.constants.security.SecurityConstants.CONFIRM_CHANGE_EMAIL_BODY;
import static com.example.onlinebookshop.constants.security.SecurityConstants.CONFIRM_CHANGE_EMAIL_SUBJECT;

import com.example.onlinebookshop.entities.ActionToken;
import com.example.onlinebookshop.repositories.ActionTokenRepository;
import com.example.onlinebookshop.services.utils.EmailLinkParameterProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChangeEmailService extends EmailService {
    private final ActionTokenRepository actionTokenRepository;
    @Value("${server.path}")
    private String serverPath;

    public ChangeEmailService(EmailLinkParameterProvider emailLinkParameterProvider,
                              ActionTokenRepository actionTokenRepository) {
        super(emailLinkParameterProvider);
        this.actionTokenRepository = actionTokenRepository;
    }

    public void sendChangeEmail(String newEmail, String oldEmail) {
        String[] paramTokenPair = getEmailLinkParameterProvider()
                .formRandomParamTokenPair(oldEmail);

        ActionToken actionToken = new ActionToken();
        actionToken.setActionToken(paramTokenPair[1] + newEmail);
        actionTokenRepository.save(actionToken);

        this.sendMessage(newEmail, CONFIRM_CHANGE_EMAIL_SUBJECT,
                CONFIRM_CHANGE_EMAIL_BODY + System.lineSeparator()
                        + serverPath + "/users/change-email-success?"
                        + paramTokenPair[FIRST_POSITION]
                        + SPLITERATOR + paramTokenPair[SECOND_POSITION]
                        + "&newEmail" + SPLITERATOR + newEmail);
    }
}
