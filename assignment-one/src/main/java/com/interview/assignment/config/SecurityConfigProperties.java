package com.interview.assignment.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@ConfigurationProperties(ignoreUnknownFields = true, prefix = "assignment.security")
@Validated
public class SecurityConfigProperties {

    @NotNull(message = "-------------- Accepted API keys configuration was not provided! --------------")
    private List<String> apiKeys;

    public List<String> getApiKeys() {
        return List.copyOf(apiKeys);
    }

    public void setApiKeys(List<String> apiKeys) {
        this.apiKeys = apiKeys;
    }
}
