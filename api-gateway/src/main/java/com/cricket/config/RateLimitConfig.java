package com.cricket.config;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitConfig {

  @Bean("userKeyResolver")
  @Primary
  public KeyResolver userKeyResolver() {
    return exchange -> {
      HttpHeaders headers = exchange.getRequest().getHeaders();
      String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        try {
          // decode JWT without verifying signature just to extract subject
          String[] parts = token.split("\\.");
          if (parts.length >= 2) {
            String payloadJson = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
            com.fasterxml.jackson.databind.JsonNode payload =
                new com.fasterxml.jackson.databind.ObjectMapper().readTree(payloadJson);
            if (payload.has("sub")) {
              return Mono.just(payload.get("sub").asText());
            }
          }
        } catch (Exception e) {
          return Mono.just("anonymous");
        }
      }
      return Mono.just("anonymous");
    };
  }
}
