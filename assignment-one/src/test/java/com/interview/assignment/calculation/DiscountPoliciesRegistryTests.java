package com.interview.assignment.calculation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("prod")
class DiscountPoliciesRegistryTests {

    @Autowired
    DiscountPoliciesRegistry registry;

    @Test
    void recognizesAmountBasedPolicy() {
        Optional<DiscountCalculator> policy = registry.getPolicy(new DiscountCriteria(DiscountCriteria.AMOUNT));

        assertInstanceOf(AmountBasedDiscountCalculator.class, policy.get());
    }

    @Test
    void recognizesPercentageBasedPolicy() {
        Optional<DiscountCalculator> policy = registry.getPolicy(new DiscountCriteria(DiscountCriteria.PERCENTAGE));

        assertInstanceOf(PercentageDiscountCalculator.class, policy.get());
    }

    @Test
    void noDefaultValueIfUnrecognizedPolicy() {
        Optional<DiscountCalculator> policy = registry.getPolicy(new DiscountCriteria("FAKE"));

        assertTrue(policy.isEmpty());
    }

}
