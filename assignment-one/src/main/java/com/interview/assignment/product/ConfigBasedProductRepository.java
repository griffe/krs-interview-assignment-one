package com.interview.assignment.product;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Profile("prod")
class ConfigBasedProductRepository implements ProductRepository {

    private final Map<UUID, Product> products;

    public ConfigBasedProductRepository(ProductConfigProperties productConfigProperties) {
        this.products = productConfigProperties.getProducts()
                .entrySet().stream()
                .map((entry) -> new Product(UUID.fromString(entry.getKey()), new BigDecimal(entry.getValue())))
                .collect(Collectors.toMap(Product::productId, product -> product));
    }

    @Override
    public Optional<Product> findProduct(UUID productId) {
        return Optional.ofNullable(products.get(productId));
    }
}
