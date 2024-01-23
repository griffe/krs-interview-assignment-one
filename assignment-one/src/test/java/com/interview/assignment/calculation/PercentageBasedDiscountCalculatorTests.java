package com.interview.assignment.calculation;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PercentageBasedDiscountCalculatorTests {

    private static Stream<Arguments> properCalculationData() {
        return Stream.of(
                arguments("5.13", "3", "0", "15.39"),
                arguments("5.234", "1000000", "100", "0"),
                arguments("5.2346", "53453", "23", "215449.9068")

        );
    }

    @Test
    void failsIfDiscountPercentageNotSpecified() {
        DiscountCalculator calculator = new PercentageDiscountCalculator();
        PriceCalculationData calculationData =
                new PriceCalculationData(new BigDecimal("100"), new BigDecimal("10"));
        PriceCalculationData.AdditionalData additionalData = new PriceCalculationData.AdditionalData(Optional.empty());

        assertInstanceOf(CalculationFailure.class, calculator.calculate(calculationData, additionalData).getLeft());
    }

    @Test
    void failsIfDiscountPercentageIsNotANumber() {
        DiscountCalculator calculator = new PercentageDiscountCalculator();
        PriceCalculationData calculationData =
                new PriceCalculationData(new BigDecimal("100"), new BigDecimal("10"));
        PriceCalculationData.AdditionalData additionalData = new PriceCalculationData.AdditionalData(Optional.of("FAIL"));

        assertInstanceOf(CalculationFailure.class, calculator.calculate(calculationData, additionalData).getLeft());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "101"})
    void failsIfDiscountPercentageOutOfRange(String discountPercentage) {
        DiscountCalculator calculator = new PercentageDiscountCalculator();
        PriceCalculationData calculationData =
                new PriceCalculationData(new BigDecimal("100"), new BigDecimal("10"));
        PriceCalculationData.AdditionalData additionalData = new PriceCalculationData.AdditionalData(Optional.of(discountPercentage));

        assertInstanceOf(CalculationFailure.class, calculator.calculate(calculationData, additionalData).getLeft());
    }

    @ParameterizedTest
    @MethodSource("properCalculationData")
    void calculatesProperly(String price, String amount, String discountPercentage, String expectedPrice) {
        DiscountCalculator calculator = new PercentageDiscountCalculator();
        PriceCalculationData calculationData =
                new PriceCalculationData(new BigDecimal(price), new BigDecimal(amount));
        PriceCalculationData.AdditionalData additionalData = new PriceCalculationData.AdditionalData(Optional.of(discountPercentage));

        Either<CalculationFailure, CalculatedPrice> result = calculator.calculate(calculationData, additionalData);

        assertInstanceOf(CalculatedPrice.class, result.get());

        assertEquals(0, result.get().price().compareTo(new BigDecimal(expectedPrice)));
    }


}
