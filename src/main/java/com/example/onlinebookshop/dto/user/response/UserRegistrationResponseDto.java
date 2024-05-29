package com.example.onlinebookshop.dto.user.response;

public record UserRegistrationResponseDto(Long id,
                                          String email,
                                          String firstName,
                                          String lastName,
                                          String shippingAddress){}
