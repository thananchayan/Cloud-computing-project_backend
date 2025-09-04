package com.cricket.service;

import com.cricket.dto.TicketBookedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSender {
  private static final Logger log = LoggerFactory.getLogger(NotificationSender.class);

  private final JavaMailSender mailSender;

  @Async
  public void sendTicketBooked(@NonNull TicketBookedEvent event) {
    // If mail isn’t configured, this will likely throw; we’ll catch and log instead
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(event.getUserEmail());
      message.setSubject("Your cricket ticket is booked!");
      message.setText("""
          Hi,
          
          Your booking is confirmed.
          Match Name  : %s
          Match Description: %s
          Seats     : %d
          Status    : %s
          
          Thank you for booking with us.
          """.formatted(event.getMatchName(),event.getDescription(), event.getSeatNumber(), event.getStatus()));
      mailSender.send(message);
      log.info("Email sent to {}", event.getUserEmail());
    } catch (Exception ex) {
      log.warn("Mail not configured or failed to send. Logging notification instead. Cause: {}", ex.getMessage());
      log.info("NOTIFY -> to: {}, matchName: {},matchDescription: {}, seat: {}, status: {}",
          event.getUserEmail(), event.getMatchName(),event.getDescription(),event.getSeatNumber(), event.getStatus());
    }
  }
}