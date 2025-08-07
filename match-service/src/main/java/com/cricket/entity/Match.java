package com.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String teamA;
  private String teamB;

  private String location;
  private LocalDateTime matchDate;
  private String time; // e.g., "14:30"
  private String format; // e.g., T20, ODI, Test
  private Integer totalSeats;
  private Integer availableSeats;
  private double ticketPrice;
  private String description;
}