package com.cricket.messaging;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  public static final String EXCHANGE = "ticket.exchange";
  public static final String ROUTING_KEY = "ticket.booked";
  public static final String NOTIFICATION_QUEUE = "notification.queue";

  @Bean
  public TopicExchange ticketExchange() {
    return new TopicExchange(EXCHANGE, true, false);
  }

  @Bean
  public Queue notificationQueue() {
    return QueueBuilder.durable(NOTIFICATION_QUEUE).build();
  }

  @Bean
  public Binding notificationBinding() {
    return BindingBuilder.bind(notificationQueue()).to(ticketExchange()).with(ROUTING_KEY);
  }

  @Bean
  public MessageConverter jacksonConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory cf, MessageConverter mc) {
    RabbitTemplate template = new RabbitTemplate(cf);
    template.setMessageConverter(mc);
    return template;
  }
}
