package com.example.onlinebookshop.services.impl;

import com.example.onlinebookshop.dto.user.request.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.response.UserResponseDto;
import com.example.onlinebookshop.entities.Role;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.RegistrationException;
import com.example.onlinebookshop.mapper.UserMapper;
import com.example.onlinebookshop.repositories.role.RoleRepository;
import com.example.onlinebookshop.repositories.user.UserRepository;
import com.example.onlinebookshop.services.ShoppingCartService;
import com.example.onlinebookshop.services.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException("User with email "
                    + requestDto.email() + " already exists");
        }
        User user = userMapper.toUser(requestDto);
        assignUserRole(user);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        userRepository.save(user);
        shoppingCartService.createShoppingCart(user);
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }

    private void assignUserRole(User user) {
        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER);
        user.setRoles(Set.of(userRole));
    }
}
