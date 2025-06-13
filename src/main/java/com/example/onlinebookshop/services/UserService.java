package com.example.onlinebookshop.services;

import com.example.onlinebookshop.dtos.role.RoleNameDto;
import com.example.onlinebookshop.dtos.user.request.UpdateUserProfileRequest;
import com.example.onlinebookshop.dtos.user.request.UserAccountStatusDto;
import com.example.onlinebookshop.dtos.user.response.UpdateUserProfileResponse;
import com.example.onlinebookshop.dtos.user.response.UserProfileAdminResponse;
import com.example.onlinebookshop.dtos.user.response.UserProfileResponse;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserProfileResponse updateUserRole(Long authenticatedUserId,
                                       Long employeeId,
                                       RoleNameDto roleNameDto);

    UserProfileResponse getProfileInfo(Long authenticatedUserId);

    UpdateUserProfileResponse updateProfileInfo(Long authenticatedUserId,
                                                UpdateUserProfileRequest updateUserProfileDto);

    UserProfileAdminResponse changeStatus(User user, Long disabledUserId,
                                          UserAccountStatusDto accountStatusDto)
            throws ForbiddenException;

    List<UserProfileResponse> getAllUsers(Pageable pageable);

    UserProfileResponse confirmEmailChange(HttpServletRequest httpServletRequest);
}
