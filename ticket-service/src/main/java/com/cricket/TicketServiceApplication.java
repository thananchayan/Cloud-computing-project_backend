package com.cricket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMethodSecurity
public class TicketServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
        System.out.println("Ticket Service is running...");
    }


    // TicketServiceApplication.java
    @Bean @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        var httpClient = reactor.netty.http.client.HttpClient.create()
            .responseTimeout(java.time.Duration.ofSeconds(4));
        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient));
    }

}
