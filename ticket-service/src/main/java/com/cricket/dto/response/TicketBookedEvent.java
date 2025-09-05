package com.cricket.dto.response;

import lombok.Data;

@Data
public class TicketBookedEvent {
  private Long matchId;
  private String matchName;
  private String description;
  private String userEmail;
  private Integer seatNumber;
  private String status; // BOOKED
}