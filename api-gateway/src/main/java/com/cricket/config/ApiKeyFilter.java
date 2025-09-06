package com.cricket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ApiKeyFilter implements GlobalFilter, Ordered {

  private final Set<String> allowedKeys;
  private final boolean enabled;

  public ApiKeyFilter(
      @Value("${security.api-key.enabled:false}") boolean enabled,
      @Value("${security.api-key.allowed-keys:}") String csvKeys) {
    this.enabled = enabled;
    this.allowedKeys = Arrays.stream(csvKeys.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toSet());
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
    if (!enabled) {
      return chain.filter(exchange);
    }

    // Allow Swagger/OpenAPI doc without API key (optional)
    String path = exchange.getRequest().getPath().value();
    if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
      return chain.filter(exchange);
    }

    String apiKey = exchange.getRequest().getHeaders().getFirst("X-API-KEY");
    if (apiKey == null || !allowedKeys.contains(apiKey)) {
      exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
      return exchange.getResponse().setComplete();
    }
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return -100; // run early
  }
}
