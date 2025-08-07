package com.cricket.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private Long id;
    private String teamA;
    private String teamB;
    private String location;
    private LocalDate matchDate;
    private LocalTime time;
    private String format;
    private Integer totalSeats;
    private Integer availableSeats;
    private String description;
    private Double ticketPrice;
}