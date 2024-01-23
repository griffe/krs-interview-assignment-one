package com.interview.assignment.calculation;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AmountBasedDiscountCalculatorTests {

    private static Stream<Arguments> properCalculationData() {
        return Stream.of(
                arguments("5.1", "1", "5.1"),
                arguments("100.1", "99", "9909.9"),
                arguments("100.2", "100", "10010"),
                arguments("100", "999", "99890"),
                arguments("123", "1000", "122950")

        );
    }

    @Test
    void failOnMissingThresholdForZero() {
        Map<BigDecimal, BigDecimal> thresholds = Map.of(BigDecimal.valueOf(10), BigDecimal.valueOf(5), BigDecimal.valueOf(100), BigDecimal.valueOf(10));

        assertThrows(IllegalStateException.class, () -> new AmountBasedDiscountCalculator(thresholds));
    }

    @Test
    void initializesProperlyIfZeroThresholdConfigured() {
        Map<BigDecimal, BigDecimal> thresholds = Map.of(BigDecimal.valueOf(10), BigDecimal.valueOf(5), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(100), BigDecimal.valueOf(10));

        new AmountBasedDiscountCalculator(thresholds);

        // succeeds
    }

    @ParameterizedTest
    @MethodSource("properCalculationData")
    void calculatesProperly(String price, String amount, String expectedPrice) {
        DiscountCalculator calculator = new AmountBasedDiscountCalculator(thresholds());

        Either<CalculationFailure, CalculatedPrice> result = calculator.calculate(new PriceCalculationData(new BigDecimal(price), new BigDecimal(amount)), new PriceCalculationData.AdditionalData(Optional.empty()));

        assertEquals(result.get().price().compareTo(new BigDecimal(expectedPrice)), 0);
    }

    private Map<BigDecimal, BigDecimal> thresholds() {
        return Map.of(BigDecimal.valueOf(0), BigDecimal.valueOf(0),
                BigDecimal.valueOf(100), BigDecimal.valueOf(10),
                BigDecimal.valueOf(1000), BigDecimal.valueOf(50));
    }


}
