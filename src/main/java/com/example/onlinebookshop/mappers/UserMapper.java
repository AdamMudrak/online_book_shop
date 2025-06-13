package com.example.onlinebookshop.mappers;

import static com.example.onlinebookshop.constants.security.SecurityConstants.CONFIRM_NEW_EMAIL_MESSAGE;

import com.example.onlinebookshop.config.MapperConfig;
import com.example.onlinebookshop.dtos.authentication.request.RegistrationRequest;
import com.example.onlinebookshop.dtos.user.response.UpdateUserProfileResponse;
import com.example.onlinebookshop.dtos.user.response.UserProfileAdminResponse;
import com.example.onlinebookshop.dtos.user.response.UserProfileResponse;
import com.example.onlinebookshop.entities.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toUser(RegistrationRequest requestDto);

    @Mapping(target = "role", source = "role.name")
    UserProfileResponse toUserProfileInfoDto(User user);

    @Mapping(target = "role", source = "role.name")
    UserProfileAdminResponse toUserProfileAdminInfoDto(User user);

    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "message", ignore = true)
    UpdateUserProfileResponse toUpdateUserProfileInfoDto(User user);

    @AfterMapping
    default void setMessage(@MappingTarget UpdateUserProfileResponse userProfileInfoDto) {
        userProfileInfoDto.setMessage(CONFIRM_NEW_EMAIL_MESSAGE);
    }
}
