package com.example.onlinebookshop.services.utils;

import static com.example.onlinebookshop.constants.security.SecurityConstants.RANDOM_LINK_STRENGTH;

import com.example.onlinebookshop.entities.ParamToken;
import com.example.onlinebookshop.repositories.ParamTokenRepository;
import com.example.onlinebookshop.security.jwtutils.abstr.JwtAbstractUtil;
import com.example.onlinebookshop.security.jwtutils.strategy.JwtStrategy;
import com.example.onlinebookshop.security.jwtutils.strategy.JwtType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class EmailLinkParameterProvider {
    private final ParamTokenRepository paramTokenRepository;
    private final JwtStrategy jwtStrategy;

    public String[] formRandomParamTokenPair(String email) {
        String[] paramTokenPair = new String[2];
        paramTokenPair[0] = RandomStringUtil.generateRandomString(RANDOM_LINK_STRENGTH);
        JwtAbstractUtil actionJwtUtil = jwtStrategy.getStrategy(JwtType.ACTION);
        paramTokenPair[1] = actionJwtUtil.generateToken(email);

        ParamToken paramToken = new ParamToken();
        paramToken.setParameter(paramTokenPair[0]);
        paramToken.setActionToken(paramTokenPair[1]);
        paramTokenRepository.save(paramToken);
        return paramTokenPair;
    }
}
