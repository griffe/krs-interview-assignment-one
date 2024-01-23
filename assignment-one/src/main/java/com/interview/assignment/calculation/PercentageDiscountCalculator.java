package com.interview.assignment.calculation;

import io.vavr.control.Either;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

class PercentageDiscountCalculator implements DiscountCalculator {

    private static final Logger logger = LogManager.getLogger(PercentageDiscountCalculator.class);

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public Either<CalculationFailure, CalculatedPrice> calculate(PriceCalculationData calculationData, PriceCalculationData.AdditionalData additionalData) {
        logger.info("Attempting to calculate price with discount: " + calculationData + ", additional data: " + additionalData);

        // TODO rounding mode question, don't use zero as default ?
        // Either one needs to have permissions to use some discount or it needs to be approved
        BigDecimal fullPrice = calculationData.price().multiply(calculationData.amount());
        return additionalData.discountPercentage()
                .filter(discountPercentage -> discountPercentage.matches("^[0-9]$|^[1-9][0-9]$|^(100)$"))
                .map(BigDecimal::new)
                .map(discountPercentage ->
                        fullPrice.subtract(fullPrice.multiply(discountPercentage).divide(ONE_HUNDRED, RoundingMode.CEILING)))
                .map(CalculatedPrice::new)
                .map(Either::<CalculationFailure, CalculatedPrice>right)
                .orElseGet(() -> Either.left(new CalculationFailure("Discount value not provided or wrong value provided. Expected number between 1 and 100.")));
    }

}
