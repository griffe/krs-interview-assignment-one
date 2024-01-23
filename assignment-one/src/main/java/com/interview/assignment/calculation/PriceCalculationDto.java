package com.interview.assignment.calculation;

import java.util.Optional;

public record PriceCalculationDto(String productId, String amount, Optional<String> discountPolicyType,
                                  Optional<String> discountPercentage) {
}
