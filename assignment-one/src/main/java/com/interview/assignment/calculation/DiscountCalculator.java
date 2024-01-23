package com.interview.assignment.calculation;

import io.vavr.control.Either;

interface DiscountCalculator {
    Either<CalculationFailure, CalculatedPrice> calculate(PriceCalculationData calculationData, PriceCalculationData.AdditionalData additionalData);

}
