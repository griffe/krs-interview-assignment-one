package com.interview.assignment.product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<Product> findProduct(UUID productId);
}
