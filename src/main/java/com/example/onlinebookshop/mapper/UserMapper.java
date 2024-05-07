package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.user.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.UserResponseDto;
import com.example.onlinebookshop.entities.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toUser(UserRegistrationRequestDto userRegistrationRequestDto);

    UserResponseDto toUserResponseDto(User user);
}
