package com.example.onlinebookshop.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {
    @Schema(name = "id", example = "1")
    private Long id;
    @Schema(name = "email", example = "example@gmail.com")
    private String email;
    @Schema(name = "firstName", example = "John")
    private String firstName;
    @Schema(name = "lastName", example = "Wick")
    private String lastName;
    @Schema(name = "shippingAddress", example = "132, My Street, Kingston, New York 12401")
    private String shippingAddress;
}
