package com.cricket.service;

import com.cricket.dto.request.TicketRequest;
import com.cricket.dto.response.ContentResponse;
import com.cricket.dto.response.Match;
import com.cricket.dto.response.TicketBookedEvent;
import com.cricket.dto.response.TicketResponse;
import com.cricket.entity.Ticket;
import com.cricket.messaging.RabbitConfig;
import com.cricket.repo.TicketRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TicketRepository ticketRepository;
  private final WebClient.Builder webClientBuilder;
  private final RabbitTemplate rabbitTemplate;


  public ContentResponse<TicketResponse> bookTicket(String userEmail, TicketRequest request,
      String authHeader) {
    ContentResponse<Match> matchResponse;
    try {
      matchResponse = webClientBuilder.build()
          .get()
          .uri("http://match-service/matches/{id}", request.getMatchId())
          .header("Authorization", authHeader) // forward JWT
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<ContentResponse<Match>>() {
          })
          .block();
    } catch (WebClientResponseException.BadRequest e) {
      return new ContentResponse<>(
          "Error",
          null,
          "ERROR",
          "400",
          "Invalid match ID or match not found"
      );
    } catch (WebClientResponseException e) {
      return new ContentResponse<>(
          "Error",
          null,
          "ERROR",
          String.valueOf(e.getStatusCode().value()),
          "Error fetching match details: " + e.getMessage()
      );
    }

    if (matchResponse == null || matchResponse.getData() == null
        || matchResponse.getData().getAvailableSeats() == null) {
      throw new IllegalArgumentException("Failed to fetch match details or available seats");
    }

    if (matchResponse.getData().getAvailableSeats() < request.getSeatNumber()) {
      throw new IllegalArgumentException("Not enough seats available");
    }
    String matchName =
        matchResponse.getData().getTeamA() + " vs " + matchResponse.getData().getTeamB();


    try {
      webClientBuilder.build()
          .put()
          .uri("http://match-service/matches/{id}/reduce?count={count}",
              request.getMatchId(), request.getSeatNumber())
          .header("Authorization", authHeader)
          .retrieve()
          .bodyToMono(Void.class)
          .block();
    } catch (WebClientResponseException e) {
      return new ContentResponse<>(
          "Error",
          null,
          "ERROR",
          String.valueOf(e.getStatusCode().value()),
          "Error updating seat count: " + e.getMessage()
      );
    }

    Ticket ticket = new Ticket();
    ticket.setMatchId(request.getMatchId());
    ticket.setSeatNumber(request.getSeatNumber());
    ticket.setUserEmail(userEmail);
    ticket.setStatus("BOOKED");

    Ticket saved = ticketRepository.save(ticket);

    // Build the event
    var event = new TicketBookedEvent();
    event.setMatchId(request.getMatchId());
    event.setMatchName(matchName);
    event.setDescription(matchResponse.getData().getDescription());
    event.setUserEmail(saved.getUserEmail());
    event.setSeatNumber(saved.getSeatNumber());
    event.setStatus(saved.getStatus());

    rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, event);


// Forward the same Authorization header received from the client
//    webClientBuilder.build()
//        .post()
//        .uri("http://notification-service/notifications/ticket-booked")
//        .header("Authorization", authHeader)
//        .bodyValue(event)
//        .retrieve()
//        .toBodilessEntity()
//        .block();

    return new ContentResponse<>(
        "Ticket",
        new TicketResponse(saved.getId(), saved.getMatchId(), saved.getUserEmail(),
            saved.getSeatNumber(), saved.getStatus()),
        "SUCCESS",
        "201",
        "Ticket booked successfully"
    );
  }

  public ContentResponse<List<TicketResponse>> getUserTickets(String userEmail) {
    List<TicketResponse> ticketResponses = ticketRepository.findByUserEmail(userEmail)
        .stream()
        .map(t -> new TicketResponse(t.getId(), t.getMatchId(), t.getUserEmail(), t.getSeatNumber(),
            t.getStatus()))
        .toList();
    return new ContentResponse<>(
        "Tickets",
        ticketResponses,
        "SUCCESS",
        "200",
        "Retrieved user tickets successfully"
    );
  }

  public ContentResponse<List<TicketResponse>> getAllTickets() {
    List<TicketResponse> ticketResponses = ticketRepository.findAll()
        .stream()
        .map(t -> new TicketResponse(t.getId(), t.getMatchId(), t.getUserEmail(), t.getSeatNumber(),
            t.getStatus()))
        .toList();
    return new ContentResponse<>(
        "Tickets",
        ticketResponses,
        "SUCCESS",
        "200",
        "Retrieved all tickets successfully"
    );
  }

}