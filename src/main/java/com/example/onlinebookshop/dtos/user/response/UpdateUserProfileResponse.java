package com.example.onlinebookshop.dtos.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileResponse extends UserProfileResponse {
    private String message;
}
