package com.example.onlinebookshop.mappers;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dtos.user.request.UserRegistrationRequestDto;
import com.example.onlinebookshop.dtos.user.response.UserRegistrationResponseDto;
import com.example.onlinebookshop.entities.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toUser(UserRegistrationRequestDto userRegistrationRequestDto);

    UserRegistrationResponseDto toUserResponseDto(User user);
}
