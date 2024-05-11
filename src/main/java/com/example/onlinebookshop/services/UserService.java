package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.user.UserLoginRequestDto;
import com.example.onlinebookshop.dto.user.UserLoginResponseDto;
import com.example.onlinebookshop.dto.user.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.UserResponseDto;
import com.example.onlinebookshop.exceptions.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
