package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dto.user.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}
