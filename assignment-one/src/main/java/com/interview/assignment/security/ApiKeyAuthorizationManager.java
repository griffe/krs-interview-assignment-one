package com.interview.assignment.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

import java.util.List;

public class ApiKeyAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private static final Logger logger = LogManager.getLogger(ApiKeyAuthorizationManager.class);

    private final List<String> acceptedApiKeys;

    public ApiKeyAuthorizationManager(List<String> acceptedApiKeys) {
        this.acceptedApiKeys = List.copyOf(acceptedApiKeys);
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        return authentication.map((it) -> {
            if (isValidApiKey(it.getCredentials().toString())) {
                return new AuthorizationDecision(true);
            } else {
                return new AuthorizationDecision(false);
            }
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return ReactiveAuthorizationManager.super.verify(authentication, object);
    }

    private boolean isValidApiKey(String apiKey) {
        return acceptedApiKeys.stream().anyMatch(apiKey::equals);
    }
}
