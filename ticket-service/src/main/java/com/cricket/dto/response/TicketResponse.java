package com.cricket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketResponse {
  private Long ticketId;
  private Long matchId;
  private String userEmail;
  private Integer seatNumber;
  private String status;
}