package com.example.onlinebookshop.services.utils;

import com.example.onlinebookshop.entities.ActionToken;
import com.example.onlinebookshop.repositories.ActionTokenRepository;
import com.example.onlinebookshop.security.jwtutils.abstr.JwtAbstractUtil;
import com.example.onlinebookshop.security.jwtutils.strategy.JwtStrategy;
import com.example.onlinebookshop.security.jwtutils.strategy.JwtType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionTokenUtil {
    private final JwtStrategy jwtStrategy;
    private final ActionTokenRepository actionTokenRepository;

    public String generateActionToken(String email) {
        JwtAbstractUtil actionUtil = jwtStrategy.getStrategy(JwtType.ACTION);
        ActionToken actionToken = new ActionToken();
        actionToken.setActionToken(actionUtil.generateToken(email));
        actionTokenRepository.save(actionToken);
        return actionToken.getActionToken();
    }

    public void saveActionTokenForNewEmail(String newEmail) {
        ActionToken actionToken = new ActionToken();
        actionToken.setActionToken(newEmail);
        actionTokenRepository.save(actionToken);
    }
}
