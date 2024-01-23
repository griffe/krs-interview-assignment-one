package com.interview.assignment.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class ApiKeyAuthenticationFilter implements WebFilter {

    private static final Logger logger = LogManager.getLogger(ApiKeyAuthenticationFilter.class);
    private static final String API_KEY_HEADER_NAME = "X-API-KEY";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String apiKey = exchange.getRequest().getHeaders().getFirst(API_KEY_HEADER_NAME);

        if (apiKey != null) {
            ApiKeyAuthenticationToken authToken = new ApiKeyAuthenticationToken(apiKey);
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
        }

        return chain.filter(exchange);
    }
}
