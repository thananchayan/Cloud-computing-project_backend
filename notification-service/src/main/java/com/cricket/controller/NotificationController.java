package com.cricket.controller;

import com.cricket.dto.TicketBookedEvent;
import com.cricket.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationSender sender;

  // NotificationController.java
  @PostMapping("/ticket-booked")
  public ResponseEntity<String> ticketBooked(@RequestBody TicketBookedEvent event) {
    sender.sendTicketBooked(event); // async fire-and-forget
    return ResponseEntity.accepted().body("Notification accepted"); // 202 fast return
  }


  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("OK");
  }
}
