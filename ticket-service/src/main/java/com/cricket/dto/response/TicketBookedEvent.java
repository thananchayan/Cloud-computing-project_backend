package com.cricket.dto.response;

import lombok.Data;

@Data
public class TicketBookedEvent {
  private Long ticketId;
  private Long matchId;
  private String userEmail;
  private Integer seatNumber;
  private String status; // BOOKED
}