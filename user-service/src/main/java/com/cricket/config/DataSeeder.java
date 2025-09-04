package com.cricket.config;

import com.cricket.entity.User;
import com.cricket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @EventListener(ApplicationReadyEvent.class)
  public void seedAdminUser() {
    try {
      userRepository.findByEmail("admin@cricket.com").orElseGet(() -> {
        User admin = new User();
        admin.setEmail("admin@cricket.com");
        admin.setPassword(passwordEncoder.encode("Admin@123"));
        admin.setRole(User.Role.ADMIN);
        return userRepository.save(admin);
      });
      System.out.println("✅ Admin user ensured in DB.");
    } catch (Exception e) {
      System.err.println("⚠️ Seeder failed: " + e.getMessage());
    }
  }
}
