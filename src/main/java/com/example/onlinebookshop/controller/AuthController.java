package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.constants.controllerconstants.AuthConstants;
import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.dto.user.request.UserLoginRequestDto;
import com.example.onlinebookshop.dto.user.request.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.response.UserLoginResponseDto;
import com.example.onlinebookshop.dto.user.response.UserResponseDto;
import com.example.onlinebookshop.exceptions.RegistrationException;
import com.example.onlinebookshop.security.AuthenticationService;
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
@Tag(name = AuthConstants.AUTH_API_NAME,
        description = AuthConstants.AUTH_API_DESCRIPTION)
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = AuthConstants.REGISTER_SUMMARY)
    @ApiResponse(responseCode = Constants.CODE_200, description = Constants.SUCCESSFULLY_REGISTERED)
    @ApiResponse(responseCode = Constants.CODE_400, description = Constants.INVALID_ENTITY_VALUE)
    @PostMapping("/registration")
    public UserResponseDto registerUser(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @Operation(summary = AuthConstants.LOGIN_SUMMARY)
    @ApiResponse(responseCode = Constants.CODE_200, description = Constants.SUCCESSFULLY_LOGGED_IN)
    @ApiResponse(responseCode = Constants.CODE_400, description = Constants.INVALID_ENTITY_VALUE)
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
