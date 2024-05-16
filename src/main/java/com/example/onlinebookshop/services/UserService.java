package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.user.request.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.response.UserResponseDto;
import com.example.onlinebookshop.exceptions.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
