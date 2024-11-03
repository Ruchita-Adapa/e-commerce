package com.app.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserRequest {
    @NotBlank(message = "Name is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
