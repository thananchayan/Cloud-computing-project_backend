package com.cricket.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank(message = "Username is required")
  @Email(message = "Invalid email format")
  private String email;      // used as username
  @NotBlank(message = "Password is required")
  private String password;
}
