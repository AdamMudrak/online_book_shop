package com.example.onlinebookshop.controller;

import static com.example.onlinebookshop.constants.AuthConstants.AUTH_API_DESCRIPTION;
import static com.example.onlinebookshop.constants.AuthConstants.AUTH_API_NAME;
import static com.example.onlinebookshop.constants.AuthConstants.REGISTER_SUMMARY;
import static com.example.onlinebookshop.constants.Constants.CODE_200;
import static com.example.onlinebookshop.constants.Constants.CODE_400;
import static com.example.onlinebookshop.constants.Constants.INVALID_ENTITY_VALUE;
import static com.example.onlinebookshop.constants.Constants.SUCCESSFULLY_REGISTERED;

import com.example.onlinebookshop.dto.user.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.UserResponseDto;
import com.example.onlinebookshop.exceptions.RegistrationException;
import com.example.onlinebookshop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = AUTH_API_NAME,
        description = AUTH_API_DESCRIPTION)
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Operation(summary = REGISTER_SUMMARY)
    @ApiResponse(responseCode = CODE_200, description = SUCCESSFULLY_REGISTERED)
    @ApiResponse(responseCode = CODE_400, description = INVALID_ENTITY_VALUE)
    @PostMapping("/registration")
    public UserResponseDto registerUser(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
