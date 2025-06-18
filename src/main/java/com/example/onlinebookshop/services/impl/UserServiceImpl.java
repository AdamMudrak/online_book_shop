package com.example.onlinebookshop.services.impl;

import static com.example.onlinebookshop.services.utils.UpdateValueValidatorUtil.areStringsValid;

import com.example.onlinebookshop.dtos.role.RoleNameDto;
import com.example.onlinebookshop.dtos.user.request.UpdateUserProfileRequest;
import com.example.onlinebookshop.dtos.user.request.UserAccountStatusDto;
import com.example.onlinebookshop.dtos.user.response.UpdateUserProfileResponse;
import com.example.onlinebookshop.dtos.user.response.UserProfileAdminResponse;
import com.example.onlinebookshop.dtos.user.response.UserProfileResponse;
import com.example.onlinebookshop.entities.Role;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.exceptions.ForbiddenException;
import com.example.onlinebookshop.mappers.UserMapper;
import com.example.onlinebookshop.repositories.ActionTokenRepository;
import com.example.onlinebookshop.repositories.RoleRepository;
import com.example.onlinebookshop.repositories.UserRepository;
import com.example.onlinebookshop.security.jwtutils.abstr.JwtAbstractUtil;
import com.example.onlinebookshop.security.jwtutils.strategy.JwtStrategy;
import com.example.onlinebookshop.security.jwtutils.strategy.JwtType;
import com.example.onlinebookshop.services.UserService;
import com.example.onlinebookshop.services.email.ChangeEmailService;
import com.example.onlinebookshop.services.utils.ParamFromHttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final ChangeEmailService changeEmailService;
    private final ParamFromHttpRequestUtil paramFromHttpRequestUtil;
    private final JwtStrategy jwtStrategy;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final ActionTokenRepository actionTokenRepository;

    @Override
    public UserProfileResponse updateUserRole(Long authenticatedUserId,
                                              Long employeeId,
                                              RoleNameDto roleNameDto) {
        if (authenticatedUserId.equals(employeeId)) {
            throw new IllegalArgumentException(
                    "To prevent unwanted damage, self-assigning of roles is restricted. "
                            + "Your own role shall be changed only manually via MySql "
                            + "Workbench or any other MySql compatible instrument");
        }
        User employee = userRepository.findById(employeeId).orElseThrow(
                () -> new EntityNotFoundException("Employee with id " + employeeId + " not found"));
        Role role = roleRepository.findByName(Role.RoleName.valueOf(roleNameDto.name()));
        employee.setRole(role);
        return userMapper.toUserProfileInfoDto(userRepository.save(employee));
    }

    @Override
    public UserProfileResponse getProfileInfo(Long authenticatedUserId) {
        return userMapper.toUserProfileInfoDto(userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Employee with id " + authenticatedUserId + " not found")));
    }

    @Override
    public UpdateUserProfileResponse updateProfileInfo(Long authenticatedUserId,
                                                UpdateUserProfileRequest updateUserProfileDto) {
        User user = userRepository.findById(authenticatedUserId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + authenticatedUserId
                        + " not found"));
        if (areStringsValid(updateUserProfileDto.firstName(), user.getFirstName())) {
            user.setFirstName(updateUserProfileDto.firstName());
        }
        if (areStringsValid(updateUserProfileDto.lastName(), user.getLastName())) {
            user.setLastName(updateUserProfileDto.lastName());
        }
        if (areStringsValid(updateUserProfileDto.email(), user.getEmail())) {
            userRepository.findByEmail(updateUserProfileDto.email())
                    .ifPresent(existingUser -> {
                        throw new IllegalArgumentException("Email " + updateUserProfileDto.email()
                                + " is already taken");
                    });
            changeEmailService.sendChangeEmail(updateUserProfileDto.email(), user.getEmail());
        }
        return userMapper.toUpdateUserProfileInfoDto(userRepository.save(user));
    }

    @Override
    public UserProfileAdminResponse changeStatus(User user, Long disabledUserId,
                                                 UserAccountStatusDto accountStatusDto)
            throws ForbiddenException {
        if (user.getId().equals(disabledUserId)) {
            throw new ForbiddenException("You can not change your own account status");
        }

        User thisUser = userRepository.findById(disabledUserId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + disabledUserId + " not found"));

        switch (accountStatusDto) {
            case LOCKED -> {
                thisUser.setEnabled(false);
                thisUser.setAccountNonLocked(false);
            }
            case NON_LOCKED -> {
                thisUser.setEnabled(true);
                thisUser.setAccountNonLocked(true);
            }
            default -> throw new IllegalArgumentException(
                    "Invalid account status " + accountStatusDto);
        }
        return userMapper.toUserProfileAdminInfoDto(userRepository.save(thisUser));
    }

    @Override
    public List<UserProfileResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserProfileInfoDto).getContent();
    }

    @Override
    public UserProfileResponse confirmEmailChange(HttpServletRequest httpServletRequest) {
        String token = paramFromHttpRequestUtil
                .parseTokenFromParam(httpServletRequest);
        JwtAbstractUtil jwtActionUtil = jwtStrategy.getStrategy(JwtType.ACTION);
        jwtActionUtil.isValidToken(token);
        String newEmail = paramFromHttpRequestUtil.getNamedParameter(httpServletRequest,
                "newEmail");
        if (!actionTokenRepository.existsByActionToken(newEmail)) {
            throw new EntityNotFoundException(
                    "No such request was found. You shouldn't change the url.");
        }
        actionTokenRepository.deleteByActionToken(newEmail);
        String email = jwtActionUtil.getUsername(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email "
                        + email + " was not found"));
        user.setEmail(newEmail);

        return userMapper.toUserProfileInfoDto(userRepository.save(user));
    }
}
