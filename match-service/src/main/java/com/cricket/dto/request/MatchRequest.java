package com.cricket.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MatchRequest {
  @NotBlank(message = "Team A is required")
  private String teamA;
  @NotBlank(message = "Team B is required")
  private String teamB;
  @NotBlank(message = "Location is required")
  private String location;
  @NotBlank(message = "Match date is required")
  private LocalDateTime matchDate;
  @NotBlank(message = "Time is required")
  private String time;        // e.g., "14:30"
  @NotBlank(message = "Format is required")
  private String format;      // e.g., T20, ODI, Test
  @NotNull(message = "Total seats are required")
  @Positive
  private Integer totalSeats;
  @NotBlank(message = "Description is required")
  private String description;
  @NotNull(message = "Price is required")
  @Positive
  private double price;
  @NotBlank(message = "Image URL is required")
  private String imageUrl;

}