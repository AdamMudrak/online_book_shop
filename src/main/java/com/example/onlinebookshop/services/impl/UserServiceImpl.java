package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.user.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.UserResponseDto;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.RegistrationException;
import com.example.onlinebookshop.mapper.UserMapper;
import com.example.onlinebookshop.repositories.user.UserRepository;
import com.example.onlinebookshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with email "
                    + requestDto.getEmail() + " already exists");
        }
        User savedUser = userRepository.save(userMapper.toUser(requestDto));
        return userMapper.toUserResponseDto(savedUser);
    }
}
