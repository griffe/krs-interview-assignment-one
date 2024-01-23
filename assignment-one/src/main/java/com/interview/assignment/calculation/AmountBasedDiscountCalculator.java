package com.interview.assignment.calculation;

import io.vavr.control.Either;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class AmountBasedDiscountCalculator implements DiscountCalculator {

    private static final Logger logger = LogManager.getLogger(AmountBasedDiscountCalculator.class);
    private final List<DiscountThreshold> thresholdsInDescendingOrder;

    public AmountBasedDiscountCalculator(Map<BigDecimal, BigDecimal> thresholds) {
        this.thresholdsInDescendingOrder = thresholds.entrySet()
                .stream().map(entry -> new DiscountThreshold(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(t -> t.threshold, Comparator.reverseOrder())).toList();
        if (this.thresholdsInDescendingOrder
                .getLast().threshold.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException("A threshold for value 0 needs to be configured");
        }
    }


    public Either<CalculationFailure, CalculatedPrice> calculate(PriceCalculationData calculationData, PriceCalculationData.AdditionalData additionalData) {
        logger.info("Attempting to calculate price with discount: " + calculationData + ", additional data: " + additionalData);
        var discount = discountLookup(calculationData);
        var price = calculationData.price().multiply(calculationData.amount()).subtract(discount);
        return Either.right(new CalculatedPrice(price));
    }

    private BigDecimal discountLookup(PriceCalculationData calculationData) {
        return thresholdsInDescendingOrder
                .stream()
                .filter(thresholdConfig -> calculationData.amount().compareTo(thresholdConfig.threshold) >= 0)
                .findFirst().map(DiscountThreshold::discount)
                .map(discount -> {
                    logger.debug("Selected discount: " + discount + " for given amount: " + calculationData.amount());
                    return discount;
                })
                .orElseThrow(() -> new IllegalStateException("A threshold for value 0 needs to be configured"));
    }

    private record DiscountThreshold(BigDecimal threshold, BigDecimal discount) {
    }
}