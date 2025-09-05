package com.cricket.messaging;

import com.cricket.dto.TicketBookedEvent;
import com.cricket.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {
  private final NotificationSender notificationSender;

  @RabbitListener(queues = RabbitConfig.NOTIFICATION_QUEUE)
  public void onTicketBooked(TicketBookedEvent event) {
    // Simulate sending email/SMS; for assignment, logging is enough
    log.info("📨 Notification: Ticket booked! matchId={}, matchName={}, matchDescription={}, user={}, seats={},status={}",
        event.getMatchId(),event.getMatchName(), event.getDescription(), event.getUserEmail(), event.getSeatNumber(),event.getStatus());

    // If you have a MailService, call it here:
    notificationSender.sendTicketBooked(event);
    // mailService.sendTicketBookedEmail(event);
  }
}
