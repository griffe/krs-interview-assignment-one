package com.interview.assignment.calculation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.interview.assignment.calculation.DiscountCriteria.AMOUNT;
import static com.interview.assignment.calculation.DiscountCriteria.PERCENTAGE;

@Component
class DiscountPoliciesRegistry {

    private static final Logger logger = LogManager.getLogger(DiscountPoliciesRegistry.class);
    private final Map<DiscountCriteria, DiscountCalculator> discountCalculators = new HashMap<>();

    DiscountPoliciesRegistry(AmountBasedDiscountConfigProperties config) {
        discountCalculators.put(new DiscountCriteria(AMOUNT), new AmountBasedDiscountCalculator(config.getThresholds()));
        discountCalculators.put(new DiscountCriteria(PERCENTAGE), new PercentageDiscountCalculator());
    }

    public Optional<DiscountCalculator> getPolicy(DiscountCriteria criteria) {
        logger.info("Looking for discount policy based on a given criteria: " + criteria);
        return Optional.ofNullable(discountCalculators.get(criteria));
    }


}
