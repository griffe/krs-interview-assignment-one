package com.interview.assignment.product;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@ConfigurationProperties(ignoreUnknownFields = true, prefix = "assignment")
@Validated
class ProductConfigProperties {

    @NotNull(message = "-------------- Products configuration was not provided! --------------")
    private Map<String, String> products;

    public Map<String, String> getProducts() {
        return products;
    }

    public void setProducts(Map<String, String> products) {
        this.products = products;
    }
}
