package com.cricket.repo;

import com.cricket.entity.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
  List<Ticket> findByUserEmail(String userEmail);
}