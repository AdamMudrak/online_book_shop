package com.example.onlinebookshop.services.email;

import static com.example.onlinebookshop.constants.security.SecurityConstants.CONFIRM_CHANGE_EMAIL_BODY;
import static com.example.onlinebookshop.constants.security.SecurityConstants.CONFIRM_CHANGE_EMAIL_SUBJECT;

import com.example.onlinebookshop.services.utils.ActionTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeEmailService extends EmailService {
    private final ActionTokenUtil actionTokenUtil;
    @Value("${server.path}")
    private String serverPath;

    public void sendChangeEmail(String newEmail, String oldEmail) {
        this.sendMessage(newEmail, CONFIRM_CHANGE_EMAIL_SUBJECT,
                CONFIRM_CHANGE_EMAIL_BODY + System.lineSeparator()
                        + serverPath + "/users/change-email-success?&token="
                        + actionTokenUtil.generateActionToken(oldEmail)
                        + "&newEmail=" + newEmail);
        actionTokenUtil.saveActionTokenForNewEmail(newEmail);
    }
}
