package com.example.onlinebookshop.services.utils;

import com.example.onlinebookshop.exceptions.ActionNotFoundException;
import com.example.onlinebookshop.repositories.ParamTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParamFromHttpRequestUtil {
    private static final int FIRST_PARAM_POSITION = 0;
    private final ParamTokenRepository paramTokenRepository;

    public String parseRandomParameterAndToken(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String randomParameter = null;
        String token = null;
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (entry.getKey().equals("newEmail")
                    || entry.getKey().equals("actionToken")) {
                continue;
            }
            randomParameter = entry.getKey();
            token = entry.getValue()[FIRST_PARAM_POSITION];
            break;
        }
        if (randomParameter != null && token != null) {
            if (!paramTokenRepository.existsByParameterAndActionToken(randomParameter, token)) {
                throw new ActionNotFoundException(
                        "No such request was found... The link might be expired or forged");
            } else {
                paramTokenRepository.deleteByParameterAndActionToken(randomParameter, token);
                return token;
            }
        } else {
            throw new ActionNotFoundException(
                    "Wasn't able to parse link...Might be expired or forged");
        }
    }

    public String getNamedParameter(HttpServletRequest request, String paramName) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return parameterMap.get(paramName)[FIRST_PARAM_POSITION];
    }
}
