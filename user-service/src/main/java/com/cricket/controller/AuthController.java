package com.cricket.controller;

import com.cricket.config.JwtUtil;
import com.cricket.dto.request.LoginRequest;
import com.cricket.dto.request.UserRequest;
import com.cricket.dto.response.ContentResponse;
import com.cricket.dto.response.LoginResponse;
import com.cricket.entity.User;
import com.cricket.repository.UserRepository;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @PostMapping("/register")
  public ContentResponse<String> register(@RequestBody @Valid UserRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new IllegalArgumentException("Email already exists");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(User.Role.USER);  // Default role USER

    userRepository.save(user);
    return new ContentResponse<>(
        "data",
        "User registered successfully",
        "SUCCESS",
        "200",
        "User registration successful"
    );
  }

  @PostMapping("/login")
  public ContentResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Invalid password");
    }

    String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setToken(token);
    loginResponse.setRole(user.getRole().name());
    loginResponse.setUserEmail(user.getEmail());
    return new ContentResponse<>(
        "data",
        loginResponse,
        "SUCCESS",
        "200",
        "Login successful"
    );
  }

  @GetMapping("/admin/dashboard")
  public ContentResponse<String> adminDashboard() {
    return new ContentResponse<>(
        "data",
        "Welcome Admin, this is your dashboard.",
        "SUCCESS",
        "200",
        "Admin dashboard accessed successfully."
    );
  }

  @GetMapping("/profile")
  public ContentResponse<String> profile() {
    return new ContentResponse<>(
        "data",
        "This is your profile information.",
        "SUCCESS",
        "200",
        "Profile accessed successfully."
    );
  }
}