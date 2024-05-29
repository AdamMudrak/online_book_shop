package com.example.onlinebookshop.mapper;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dto.user.request.UserRegistrationRequestDto;
import com.example.onlinebookshop.dto.user.response.UserRegistrationResponseDto;
import com.example.onlinebookshop.entities.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toUser(UserRegistrationRequestDto userRegistrationRequestDto);

    UserRegistrationResponseDto toUserResponseDto(User user);
}
