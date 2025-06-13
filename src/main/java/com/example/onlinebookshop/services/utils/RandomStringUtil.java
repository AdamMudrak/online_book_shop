package com.example.onlinebookshop.services.utils;

import java.util.Random;

import static com.example.onlinebookshop.constants.security.SecurityConstants.RANDOM_STRING_BASE;

public class RandomStringUtil {
    public static String generateRandomString(int strength) {
        String characters = RANDOM_STRING_BASE;
        StringBuilder randomString =
                new StringBuilder(strength);
        Random random = new Random();
        for (int i = 0; i < strength; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }
        return randomString.toString();
    }
}
