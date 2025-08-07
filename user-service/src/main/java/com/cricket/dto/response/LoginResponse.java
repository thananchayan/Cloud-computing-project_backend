package com.cricket.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String userEmail;
    private String role;


}
