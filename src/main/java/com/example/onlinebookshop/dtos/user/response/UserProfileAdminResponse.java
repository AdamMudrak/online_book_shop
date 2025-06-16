package com.example.onlinebookshop.dtos.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileAdminResponse extends UserProfileResponse {
    private boolean isEnabled;
    private boolean isAccountNonLocked;
}
