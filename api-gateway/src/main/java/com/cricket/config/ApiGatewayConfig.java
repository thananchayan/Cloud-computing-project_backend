package com.cricket.config;


import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;


@Configuration
public class ApiGatewayConfig {

  // Simple per-IP rate limiting:
  @Bean("ipKeyResolver")
  public KeyResolver ipKeyResolver() {
    return exchange -> Mono.just(
        exchange.getRequest().getRemoteAddress() != null
            ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
            : "unknown");
  }

  // Or per API key (X-API-KEY) – uncomment to use:
  // @Bean
  // public KeyResolver apiKeyResolver() {
  //   return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("X-API-KEY"))
  //       .switchIfEmpty(Mono.just("anonymous"));
  // }
}