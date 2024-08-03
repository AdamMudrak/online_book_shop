package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dtos.user.request.UserRegistrationRequestDto;
import com.example.onlinebookshop.dtos.user.response.UserRegistrationResponseDto;
import com.example.onlinebookshop.exceptions.RegistrationException;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
