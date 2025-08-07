package com.cricket.config;

import com.cricket.entity.User;
import com.cricket.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataSeeder implements CommandLineRunner {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  @Override
  public void run(String... args) {

    // Step 3: Create Admin User with ADMIN role
    createAdminUserIfNotExists();
  }

  private void createAdminUserIfNotExists() {
    userRepository.findByEmail("admin@cricket.com").orElseGet(() -> {
      User admin = new User();
      admin.setEmail("admin@cricket.com");
      admin.setPassword(passwordEncoder.encode("Admin@123")); // Default password
      admin.setRole(User.Role.ADMIN);
      userRepository.save(admin);

      return userRepository.save(admin);
    });
  }

}
