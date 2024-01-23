package com.interview.assignment.product;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID productId, BigDecimal price) {
}
