package com.example.onlinebookshop.dto.user.response;

public record UserResponseDto(Long id,
                              String email,
                              String firstName,
                              String lastName,
                              String shippingAddress){}
