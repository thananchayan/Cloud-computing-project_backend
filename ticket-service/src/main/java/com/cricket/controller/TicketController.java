package com.cricket.controller;

import com.cricket.dto.request.TicketRequest;
import com.cricket.dto.response.ContentResponse;
import com.cricket.dto.response.TicketResponse;
import com.cricket.service.TicketService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

  private final TicketService ticketService;

  @PostMapping("/book")
  public ContentResponse<TicketResponse> bookTicket(@RequestBody @Valid TicketRequest request,
      @RequestHeader("Authorization") String authHeader) {
    String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ticketService.bookTicket(userEmail, request, authHeader);
  }


  @GetMapping("/my")
  public ContentResponse<List<TicketResponse>> myTickets() {
    String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ticketService.getUserTickets(userEmail);
  }

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN')")
  public ContentResponse<List<TicketResponse>> getAllTickets() {
    return ticketService.getAllTickets();
  }

  @GetMapping("/test")
  public String test() {
    String instanceId = System.getenv("HOSTNAME"); // Docker container ID
    return "Response from Ticket Service instance: " + instanceId;
  }
}