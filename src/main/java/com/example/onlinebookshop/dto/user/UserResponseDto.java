package com.example.onlinebookshop.dto.user;

import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.constants.UserDtoConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {
    @Schema(name = Constants.ID, example = Constants.ID_EXAMPLE)
    private Long id;
    @Schema(name = UserDtoConstants.EMAIL, example = UserDtoConstants.EMAIL_EXAMPLE)
    private String email;
    @Schema(name = UserDtoConstants.FIRST_NAME, example = UserDtoConstants.FIRST_NAME_EXAMPLE)
    private String firstName;
    @Schema(name = UserDtoConstants.LAST_NAME, example = UserDtoConstants.LAST_NAME_EXAMPLE)
    private String lastName;
    @Schema(name = UserDtoConstants.SHIPPING_ADDRESS,
            example = UserDtoConstants.SHIPPING_ADDRESS_EXAMPLE)
    private String shippingAddress;
}
