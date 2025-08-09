package com.cricket.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequest {
  @NotNull(message = "Match ID is required")
  private Long matchId;
  @NotNull(message = "Seat Count is required")
  @Min(value = 1, message = "Seat Count must be at least 1")
  private Integer seatNumber;
}
