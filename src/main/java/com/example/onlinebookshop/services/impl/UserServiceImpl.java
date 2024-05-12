package com.example.onlinebookshop.services.impl;

import static com.example.onlinebookshop.constants.AuthConstants.AUTH_EXCEPTION_MESSAGE;

import com.example.onlinebookshop.dto.user.UserLoginRequestDto;
import com.example.onlinebookshop.dto.user.UserLoginResponseDto;
import com.example.onlinebookshop.dto.user.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.UserResponseDto;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.AuthenticationException;
import com.example.onlinebookshop.exceptions.RegistrationException;
import com.example.onlinebookshop.mapper.UserMapper;
import com.example.onlinebookshop.repositories.user.UserRepository;
import com.example.onlinebookshop.security.JwtUtil;
import com.example.onlinebookshop.services.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with email "
                    + requestDto.getEmail() + " already exists");
        }
        User user = userMapper.toUser(requestDto);
        user.setRoles( // set USER role
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
        User userWithEncodedPassword = userMapper.toUser(requestDto);
        User savedUser = userRepository.save(userWithEncodedPassword);
        return userMapper.toUserResponseDto(savedUser);
    }

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        Optional<User> user = userRepository.findByEmail(requestDto.email());
        if (user.isEmpty()) {
            throw new AuthenticationException(AUTH_EXCEPTION_MESSAGE);
        }
        String rawPassword = requestDto.password();
        String userPasswordFromDb = user.get().getPassword();
        if (!passwordEncoder.matches(rawPassword, userPasswordFromDb)) {
            throw new AuthenticationException(AUTH_EXCEPTION_MESSAGE);
        }
        return new UserLoginResponseDto(jwtUtil.generateToken(requestDto.email()));
    }
}
