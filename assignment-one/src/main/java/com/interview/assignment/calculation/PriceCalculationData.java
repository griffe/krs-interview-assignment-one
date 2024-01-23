package com.interview.assignment.calculation;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public record PriceCalculationData(@NotNull BigDecimal price, @NotNull BigDecimal amount) {
    public record AdditionalData(@NotNull Optional<String> discountPercentage) {

        static AdditionalData from(PriceCalculationDto calculationRequest) {
            return new AdditionalData(calculationRequest.discountPercentage());
        }

    }
}
